package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.VisitaDto;
import br.com.ronna.control.models.VisitaModel;
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
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
@RequestMapping("/visita")
@CrossOrigin(value = "*", maxAge = 3600)
public class VisitaController {

    @Autowired
    VisitaService visitaService;

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

    @PostMapping("/novo")
    public ResponseEntity<Object> criarVisita(@RequestBody VisitaDto visitaDto) {
        var visitaModel = new VisitaModel();
        BeanUtils.copyProperties(visitaDto, visitaModel);
        visitaModel.setCreatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        visitaModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));

        visitaService.save(visitaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaModel);
    }

    @PutMapping("{visitaId}")
    public ResponseEntity<Object> criarVisita(@RequestBody VisitaDto visitaDto, @PathVariable(value = "visitaId") UUID visitaId) {
        var visitaModelOptional = visitaService.findById(visitaId);
        if(!visitaModelOptional.isPresent()) {
            log.debug("Erro: Visita com id {} não encontrada!", visitaId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Visita não encontrada!");
        }

        BeanUtils.copyProperties(visitaDto, visitaModelOptional.get());
        visitaModelOptional.get().setCreatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        visitaModelOptional.get().setUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));

        visitaService.save(visitaModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(visitaModelOptional.get());
    }

}
