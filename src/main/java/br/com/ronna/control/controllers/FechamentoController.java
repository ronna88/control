package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.PeriodoDto;
import br.com.ronna.control.models.VisitaModel;
import br.com.ronna.control.services.ClienteService;
import br.com.ronna.control.services.FuncionarioService;
import br.com.ronna.control.services.LocalService;
import br.com.ronna.control.services.VisitaService;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    }

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
    }

}
