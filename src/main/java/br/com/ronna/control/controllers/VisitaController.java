package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.PeriodoDto;
import br.com.ronna.control.dtos.VisitaDto;
import br.com.ronna.control.models.FuncionarioModel;
import br.com.ronna.control.models.VisitaModel;
import br.com.ronna.control.services.ClienteService;
import br.com.ronna.control.services.FuncionarioService;
import br.com.ronna.control.services.LocalService;
import br.com.ronna.control.services.VisitaService;
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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@Log4j2
@RequestMapping("/visita")
@CrossOrigin(value = "*", maxAge = 3600)
public class VisitaController {

    @Autowired
    VisitaService visitaService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    FuncionarioService funcionarioService;

    @Autowired
    LocalService localService;

    @GetMapping
    public ResponseEntity<Page<VisitaModel>> listaTodasVisitas(@PageableDefault(page = 0, size = 100, sort = "visitaInicio", direction = Sort.Direction.ASC)Pageable pageable) {
        log.debug("Listando todas as visitas...");

        Page<VisitaModel> visitaModelPage = visitaService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(visitaModelPage);
    }

    @GetMapping("/{visitaId}")
    public ResponseEntity<Object> buscaVisita(@PathVariable (value = "visitaId")UUID visitaId){
        var visitaModelOptional = visitaService.findById(visitaId);
        if(!visitaModelOptional.isPresent()) {
            log.warn("Erro: Visita com ID: {} não encontrada!", visitaId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Visita não encontrada!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(visitaModelOptional.get());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Object> listarVisitasPorClienteEPeriodo(@PathVariable (value = "clienteId") UUID clienteId, @RequestBody PeriodoDto periodoDto,
                                                                  @PageableDefault(page = 0, size = 100, sort = "visitaInicio", direction = Sort.Direction.ASC) Pageable pageable) {

        var clienteModelOptional = clienteService.findById(clienteId);
        if(!clienteModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Cliente selecionado não encontrado!");
        }

        Page<VisitaModel> visitaModelPage = visitaService.listarVisitasPorClienteEPeriodo(clienteModelOptional.get(), periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(visitaModelPage);
        //return ResponseEntity.status(HttpStatus.OK).body(visitaService.listarVisitasPorClienteEPeriodo(clienteId, periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal()));
    }


    @PostMapping("/novo")
    public ResponseEntity<Object> criarVisita(@RequestBody VisitaDto visitaDto) {
        var visitaModel = new VisitaModel();
        BeanUtils.copyProperties(visitaDto, visitaModel);

        var clienteModelOptional = clienteService.findById(visitaDto.getCliente());
        if(!clienteModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Cliente não encontrado!");
        }
        visitaModel.setCliente(clienteModelOptional.get());

        var localModelOptional = localService.findById(visitaDto.getLocal());
        if(localModelOptional.isPresent()) {
            visitaModel.setLocal(localModelOptional.get());
        }

        Set<FuncionarioModel> funcTemp = new HashSet<>();
        AtomicBoolean funcionarioTeste = new AtomicBoolean(false);
        visitaDto.getFuncionarios().forEach(f -> {
            var funcionarioModelOptinal = funcionarioService.findById(f.getFuncionarioId());
            if(!funcionarioModelOptinal.isPresent()){
                funcionarioTeste.set(true);
                return;
            }
            funcTemp.add(funcionarioModelOptinal.get());
        });
        if(funcionarioTeste.get()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Funcionário não encontrado!");
        }
        visitaModel.setVisitaRemoto(visitaDto.isVisitaRemoto());
        visitaModel.setFuncionarios(funcTemp);
        visitaModel.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        visitaModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        visitaService.save(visitaModel);
        log.debug(visitaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaModel);
    }

    @PutMapping("/{visitaId}")
    public ResponseEntity<Object> editarVisita(@RequestBody VisitaDto visitaDto, @PathVariable(value = "visitaId") UUID visitaId) {
        var visitaModelOptional = visitaService.findById(visitaId);
        if(!visitaModelOptional.isPresent()) {
            log.debug("Erro: Visita com id {} não encontrada!", visitaId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Visita não encontrada!");
        }

        BeanUtils.copyProperties(visitaDto, visitaModelOptional.get());


        var clienteModel = clienteService.findById(visitaDto.getCliente());
        visitaModelOptional.get().setCliente(clienteModel.get());

        var localModelOptional = localService.findById(visitaDto.getLocal());
        if(localModelOptional.isPresent()) {
            visitaModelOptional.get().setLocal(localModelOptional.get());
        }

        Set<FuncionarioModel> funcTemp = new HashSet<>();
        visitaDto.getFuncionarios().forEach(f -> {
            // var funcionarioModelOptinal = funcionarioService.findById(f.getFuncionarioId());
            // var funcionarioModelOptinal = funcionarioService.findById(f);
            // if(funcionarioModelOptinal.isPresent()){
            //    funcTemp.add(funcionarioModelOptinal.get());
            // }
            var funcionarioModelOptinal = funcionarioService.findById(f.getFuncionarioId());
            if(funcionarioModelOptinal.isPresent()){
                funcTemp.add(f);
            }
        });
        visitaModelOptional.get().setFuncionarios(funcTemp);

        visitaModelOptional.get().setVisitaRemoto(visitaDto.isVisitaRemoto());

        visitaModelOptional.get().setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        visitaService.save(visitaModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaModelOptional.get());
    }

    // Mapeamento de endpoint para o fechamento.
    @GetMapping("/clientelocal/{clienteLocalId}")
    public ResponseEntity<Object> getVisitasPorClienteLocalEPeriodo(@PathVariable (value = "clienteLocalId") UUID clienteLocalId, @RequestBody PeriodoDto periodoDto,
                                                                    @PageableDefault(page = 0, size = 100, sort = "visita_inicio", direction = Sort.Direction.ASC) Pageable pageable) {

        // Validação do UUID do local recebido como parametro.
        var clienteLocalModel = localService.findById(clienteLocalId);
        if(!clienteLocalModel.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Local do Cliente selecionado não encontrado!");
        }

        Page<VisitaModel> visitaModelPage = visitaService.listarVisitasPorClienteLocalEPeriodo(clienteLocalId, periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(visitaModelPage);
    }

}