package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.FechamentoDto;
import br.com.ronna.control.dtos.FechamentoStatusDto;
import br.com.ronna.control.enums.FechamentoStatus;
import br.com.ronna.control.models.FechamentoModel;
import br.com.ronna.control.models.VisitaModel;
import br.com.ronna.control.services.FechamentoService;
import br.com.ronna.control.services.LocalService;
import br.com.ronna.control.utils.CalculoHoras;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.var;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/fechamento")
public class FechamentoController {

    @Autowired
    FechamentoService fechamentoService;

    @Autowired
    LocalService localService;

    @GetMapping("/{clienteLocalId}")
    public ResponseEntity<Object> listarFechamentosClienteLocal(@PathVariable(value = "clienteLocalId")UUID clienteLocalId,
                                                                @PageableDefault(page = 0, size = 100, sort = "fechamentoIncio", direction = Sort.Direction.ASC)Pageable pageable) {
        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            log.info("Local do cliente {} não encontrado!", clienteLocalId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        Page<FechamentoModel> fechamentoModelPage = fechamentoService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(fechamentoModelPage);
    }

    @GetMapping("/{clienteLocalId}/{fechamentoId}")
    public ResponseEntity<Object> buscarFechamento(@PathVariable(value = "clienteLocalId") UUID clienteLocalId, @PathVariable(value = "fechamentoId") UUID fechamentoId) {
        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            log.info("Local do cliente {} não encontrado!", clienteLocalId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        var fechamentoModelOptional = fechamentoService.findById(fechamentoId);
        if(!fechamentoModelOptional.isPresent()) {
            log.info("Fechamento {} não encontrado!", fechamentoId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Fechamento selecionado não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(fechamentoModelOptional.get());
    }

    @PostMapping("/novo")
    public ResponseEntity<Object> criarFechamento(@RequestBody FechamentoDto fechamentoDto) {
        log.info("Criação de fechamento...");

        var localModelOptional = localService.findById(fechamentoDto.getClienteLocalId());
        if(!localModelOptional.isPresent()) {
            log.info("Local do cliente {} não encontrado!", fechamentoDto.getClienteLocalId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }

        Double valorRemoto = localModelOptional.get().getCliente().getContrato().getContratoValorRemoto();
        Double valorVisita = localModelOptional.get().getCliente().getContrato().getContratoValorVisita();
        Double totalMinutosRemoto = 0.0;
        Double totalMinutosVisita = 0.0;
        Double valorProdutos = 0.0;
        CalculoHoras calc = new CalculoHoras();

        var fechamentoModel = new FechamentoModel();
        BeanUtils.copyProperties(fechamentoDto, fechamentoModel);
        fechamentoModel.setFechamentoStatus(FechamentoStatus.CRIADO);
        fechamentoModel.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        fechamentoModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        for (VisitaModel visita : fechamentoDto.getVisitas()) {
            if(visita.isVisitaRemoto()) {
                totalMinutosRemoto += calc.diferencaHoras(visita.getVisitaInicio(), visita.getVisitaFinal(), visita.getVisitaTotalAbono());
            } else {
                totalMinutosVisita += calc.diferencaHoras(visita.getVisitaInicio(), visita.getVisitaFinal(), visita.getVisitaTotalAbono());
            }
            valorProdutos += visita.getVisitaValorProdutos();
        }

        fechamentoModel.setFechamentoValorProdutos(valorProdutos);
        fechamentoModel.setFechamentoValorServicos((totalMinutosRemoto*valorRemoto) + (totalMinutosVisita*valorVisita));

        fechamentoService.save(fechamentoModel);
        log.info("Fechamento criado com sucesso fechamentoId: {}", fechamentoModel.getFechamentoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(fechamentoModel);
    }

    @PutMapping("/{clienteLocalId}/editstatus/{fechamentoId}")
    public ResponseEntity<Object> editarStatusFechamento(@PathVariable(value = "clienteLocalId") UUID clienteLocalId, @PathVariable(value = "fechamentoId") UUID fechamentoId, @RequestBody FechamentoStatusDto fechamentoStatusDto) {
        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            log.info("Local do cliente {} não encontrado!", clienteLocalId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        var fechamentoModelOptional = fechamentoService.findById(fechamentoId);
        if(!fechamentoModelOptional.isPresent()) {
            log.info("Fechamento {} não encontrado!", fechamentoId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Fechamento não encontrado!");
        }
        fechamentoModelOptional.get().setFechamentoStatus(fechamentoStatusDto.getFechamentoStatus());
        fechamentoModelOptional.get().setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        fechamentoService.save(fechamentoModelOptional.get());
        log.info("Status do fechamento atualizado com sucesso para {}", fechamentoStatusDto.getFechamentoStatus());
        return ResponseEntity.status(HttpStatus.OK).body(fechamentoModelOptional.get());
    }

    @PutMapping("/{clienteLocalId}/editar/{fechamentoId}")
    public ResponseEntity<Object> editarFechamento(@PathVariable(value = "clienteLocalId") UUID clienteLocalId, @PathVariable(value = "fechamentoId") UUID fechamentoId, @RequestBody FechamentoDto fechamentoDto) {
        var localModelOptional = localService.findById(clienteLocalId);
        if(!localModelOptional.isPresent()) {
            log.info("Local do cliente {} não encontrado!", clienteLocalId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }
        var fechamentoModelOptional = fechamentoService.findById(fechamentoId);
        if(!fechamentoModelOptional.isPresent()) {
            log.info("Fechamento {} não encontrado!", fechamentoId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Fechamento não encontrado!");
        }

        Double valorRemoto = localModelOptional.get().getCliente().getContrato().getContratoValorRemoto();
        Double valorVisita = localModelOptional.get().getCliente().getContrato().getContratoValorVisita();
        Double totalMinutosRemoto = 0.0;
        Double totalMinutosVisita = 0.0;
        Double valorProdutos = 0.0;
        CalculoHoras calc = new CalculoHoras();
        BeanUtils.copyProperties(fechamentoDto, fechamentoModelOptional.get());

        for (VisitaModel visita : fechamentoDto.getVisitas()) {
            if(visita.isVisitaRemoto()) {
                totalMinutosRemoto += calc.diferencaHoras(visita.getVisitaInicio(), visita.getVisitaFinal(), visita.getVisitaTotalAbono());
            } else {
                totalMinutosVisita += calc.diferencaHoras(visita.getVisitaInicio(), visita.getVisitaFinal(), visita.getVisitaTotalAbono());
            }
            valorProdutos += visita.getVisitaValorProdutos();
        }

        fechamentoModelOptional.get().setFechamentoValorProdutos(valorProdutos);
        fechamentoModelOptional.get().setFechamentoValorServicos((totalMinutosRemoto*valorRemoto) + (totalMinutosVisita*valorVisita));
        fechamentoModelOptional.get().setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        fechamentoService.save(fechamentoModelOptional.get());
        log.info("Fechamento atualizado com sucesso! {}", fechamentoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(fechamentoModelOptional.get());
    }

    @DeleteMapping("/apagar/{fechamentoId}")
    public ResponseEntity<Object> apagarFechamento(@PathVariable(value = "fechamentoId") UUID fechamentoId){
        var fechamentoModelOptional = fechamentoService.findById(fechamentoId);
        if(!fechamentoModelOptional.isPresent()) {
            log.info("Fechamento {} não encontrado!", fechamentoId);
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Fechamento não encontrado!");
        }
        fechamentoService.delete(fechamentoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Fechamento apagado com suceso!");
    }

}