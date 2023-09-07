package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.PeriodoDto;
import br.com.ronna.control.dtos.VisitaDto;
import br.com.ronna.control.models.FuncionarioModel;
import br.com.ronna.control.models.VisitaModel;
import br.com.ronna.control.services.ClienteService;
import br.com.ronna.control.services.FuncionarioService;
import br.com.ronna.control.services.VisitaService;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<VisitaModel>> listaTodasVisitas() {
        log.debug("Listando todas as visitas...");
        return ResponseEntity.status(HttpStatus.OK).body(visitaService.buscaTodasVisitas());
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
    public ResponseEntity<Object> listarVisitasPorClienteEPeriodo(@PathVariable (value = "clienteId") UUID clienteId, @RequestBody PeriodoDto periodoDto) {
        log.error(clienteId);
        log.error(periodoDto.getPeriodoInicio());
        log.error(periodoDto.getPeriodoFinal());
        return ResponseEntity.status(HttpStatus.OK).body(visitaService.listarVisitasPorClienteEPeriodo(clienteId, periodoDto.getPeriodoInicio(), periodoDto.getPeriodoFinal()));
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

        Set<FuncionarioModel> funcTemp = new HashSet<>();
        visitaDto.getFuncionarios().forEach(f -> {
            var funcionarioModelOptinal = funcionarioService.findById(f.getFuncionariosId());
            if(funcionarioModelOptinal.isPresent()){
                funcTemp.add(f);
            }
        });
        visitaModel.setFuncionarios(funcTemp);
        visitaModel.setCreatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        visitaModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        visitaService.save(visitaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaModel);
    }

    @PutMapping("/{visitaId}")
    public ResponseEntity<Object> criarVisita(@RequestBody VisitaDto visitaDto, @PathVariable(value = "visitaId") UUID visitaId) {
        var visitaModelOptional = visitaService.findById(visitaId);
        if(!visitaModelOptional.isPresent()) {
            log.debug("Erro: Visita com id {} não encontrada!", visitaId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Visita não encontrada!");
        }

        BeanUtils.copyProperties(visitaDto, visitaModelOptional.get());


        var clienteModel = clienteService.findById(visitaDto.getCliente());
        visitaModelOptional.get().setCliente(clienteModel.get());

        Set<FuncionarioModel> funcTemp = new HashSet<>();
        visitaDto.getFuncionarios().forEach(f -> {
            var funcionarioModelOptinal = funcionarioService.findById(f.getFuncionariosId());
            if(funcionarioModelOptinal.isPresent()){
                funcTemp.add(f);
            }
        });
        visitaModelOptional.get().setFuncionarios(funcTemp);

        visitaModelOptional.get().setUpdatedDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        visitaService.save(visitaModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaModelOptional.get());
    }

}
