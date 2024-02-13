package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.FechamentoDto;
import br.com.ronna.control.dtos.PeriodoDto;
import br.com.ronna.control.enums.FechamentoStatus;
import br.com.ronna.control.models.FechamentoModel;
import br.com.ronna.control.models.VisitaModel;
import br.com.ronna.control.services.*;
import br.com.ronna.control.utils.CalculoHoras;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/fechamento")
@CrossOrigin(value = "*", maxAge = 3600)
public class FechamentoController {

    @Autowired
    VisitaService visitaService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    LocalService localService;

    @Autowired
    FechamentoService fechamentoService;

    @GetMapping("/{clienteLocalId}")
    public ResponseEntity<Object> listarFechamentos(@PathVariable (value = "clienteLocalId") UUID clienteLocalId,
                                                    @RequestBody PeriodoDto periodoDto,
                                                    @PageableDefault(page = 0, size = 100, sort = "fechamentoInicio", direction = Sort.Direction.ASC) Pageable pageable) {

        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        Page<FechamentoModel> fechamentoModelPage = fechamentoService.findAllByClienteLocalIdAndPeriodo(clienteLocalId, periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(fechamentoModelPage);
    }

    @PutMapping("/editar/{clienteLocalId}/{fechamentoId}")
    public ResponseEntity<Object> editarFechamento(@PathVariable(value="clienteLocalId") UUID clienteLocalId,
                                                   @PathVariable(value = "fechamentoId") UUID fechamentoId, @RequestBody FechamentoDto fechamentoDto) {
        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }

        var fechamentoOptional = fechamentoService.findById(fechamentoId);
        if(!fechamentoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Fechamento não encontrado!");
        }

        BeanUtils.copyProperties(fechamentoDto, fechamentoOptional.get());
        fechamentoOptional.get().setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        fechamentoService.save(fechamentoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(fechamentoOptional.get());
    }

    @DeleteMapping("/{clienteLocalId}/{fechamentoId}")
    public ResponseEntity<Object> deletarFechamento(@PathVariable(value = "clienteLocalId") UUID clienteLocalId,
                                                    @PathVariable(value = "fechamentoId") UUID fechamentoId) {
        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        var fechamentoOptional = fechamentoService.findById(fechamentoId);
        if(!fechamentoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Fechamento não encontrado!");
        }
        fechamentoService.delete(fechamentoOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Fechamento excluído com sucesso!");
    }

    @PostMapping("/novo")
    public ResponseEntity<Object> criarFechamento(@RequestBody FechamentoDto fechamentoDto) {
        log.debug("Criação de fechamento...");

        var localModelOptional = localService.findById(fechamentoDto.getClienteLocalId());
        if(!localModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        Double valorRemoto = localModelOptional.get().getCliente().getContrato().getContratoValorRemoto();
        Double valorVisita = localModelOptional.get().getCliente().getContrato().getContratoValorVisita();

        Double valorServico = 0.0;
        Double valorProduto = 0.0;
        Double totalMinutosRemoto = 0.0;
        Double totalMinutosVisita = 0.0;

        CalculoHoras calc = new CalculoHoras();

        for(VisitaModel visita : fechamentoDto.getVisitas()) {
            valorProduto += visita.getVisitaValorProdutos();
            if(visita.isVisitaRemoto()){
                totalMinutosRemoto += calc.diferencaHoras(visita.getVisitaFinal(), visita.getVisitaInicio());
            } else {
                totalMinutosVisita += calc.diferencaHoras(visita.getVisitaFinal(), visita.getVisitaInicio());
            }
        }

        var fechamentoModel = new FechamentoModel();
        BeanUtils.copyProperties(fechamentoDto, fechamentoModel);

        valorServico += (( totalMinutosRemoto * valorRemoto ) + ( totalMinutosVisita * valorServico ));
        fechamentoModel.setValorProdutos(valorProduto);
        fechamentoModel.setValorServicos(valorServico);

        fechamentoModel.setFechamentoStatus(FechamentoStatus.CRIADO);
        fechamentoModel.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        fechamentoModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        fechamentoService.save(fechamentoModel);
        log.debug("POST criarFechamento fechamentoModel salvo {}", fechamentoModel.toString());
        log.info("Fechamento criado com sucesso fechamentoId {}", fechamentoModel.getFechamentoId());

        return ResponseEntity.status(HttpStatus.CREATED).body(fechamentoModel);
    }


    /*
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Object> listarVisitasPorClienteEPeriodo(@PathVariable (value = "clienteId") UUID clienteId,
                                                                  @RequestBody PeriodoDto periodoDto,
                                                                  @PageableDefault(page = 0, size = 100, sort = "visitaInicio", direction = Sort.Direction.ASC) Pageable pageable) {


        var clienteModelOptional = clienteService.findById(clienteId);
        if(!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Cliente selecionado não encontrado!");
        }

        Page<VisitaModel> visitaModelPage = visitaService.listarVisitasPorClienteEPeriodo(clienteModelOptional.get(), periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(visitaModelPage);
    } */

    /*
    @GetMapping("/cliente/{clienteId}/local/{localId}")
    public ResponseEntity<Object> listarVisitasPorClienteELocalEPeriodo(@PathVariable (value = "clienteId") UUID clienteId, @PathVariable (value = "localId") UUID localId,
                                                                        @RequestBody PeriodoDto periodoDto,
                                                                        @PageableDefault(page = 0, size = 100, sort = "visitaInicio", direction = Sort.Direction.ASC) Pageable pageable) {

        var clienteModelOptional = clienteService.findById(clienteId);
        if(!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Cliente selecionado não encontrado!");
        }

        var localModelOptional = localService.findById(localId);
        if(!localModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local selecionado não pertence ao cliente ou local não existe!");
        }

        Page<VisitaModel> visitaModelPage = visitaService.listarVisitasPorClienteLocalEPeriodo(clienteModelOptional.get(), localModelOptional.get(), periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(visitaModelPage);
    } */

}
