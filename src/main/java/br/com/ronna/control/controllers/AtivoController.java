package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.AtivoDto;
import br.com.ronna.control.models.AtivoModel;
import br.com.ronna.control.services.AtivoService;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.apache.catalina.connector.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ativo")
public class AtivoController {

    @Autowired
    AtivoService ativoService;

    @GetMapping()
    public ResponseEntity<List<AtivoModel>> buscarTodosAtivos(){
        log.debug("Listando todos os ativos...");
        return ResponseEntity.status(HttpStatus.OK).body(ativoService.findAll());
    }

    @GetMapping("/{ativoId}")
    public ResponseEntity<Object> buscarAtivo(@PathVariable (value = "ativoId")UUID ativoId){
        log.debug("Buscando ativo UUID: {}", ativoId);
        Optional<AtivoModel> ativoModelOptional = ativoService.findById(ativoId);
        if(!ativoModelOptional.isPresent()){
            log.info("Ativo {} não encontrado!", ativoId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ativo não encontrado!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(ativoModelOptional.get());
        }
    }

    @PostMapping("/novo")
    public ResponseEntity<Object> criarAtivo(@RequestBody AtivoDto ativoDto) {
        log.debug("Criação de ativo...");

        var ativoModel = new AtivoModel();
        BeanUtils.copyProperties(ativoDto, ativoModel);
        ativoModel.setAtivoDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        ativoModel.setAtivoDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));

        ativoService.save(ativoModel);
        log.debug("POST criarAtivo ativoModel salvo {}", ativoModel.toString());
        log.info("Ativo criado com sucesso ativoId {}", ativoModel.getAtivoId());

        return ResponseEntity.status(HttpStatus.CREATED).body(ativoModel);
    }

    @PutMapping("/{ativoId}")
    public ResponseEntity<Object> editarAtivo(@PathVariable (value = "ativoId") UUID ativoId, @RequestBody AtivoDto ativoDto) {
        log.debug("Editar ativoId {}  ...", ativoId);

        Optional<AtivoModel> ativoModelOptional = ativoService.findById(ativoId);
        if(!ativoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ativo não encontrado!");
        } else {
            var ativoModel = ativoModelOptional.get();
            BeanUtils.copyProperties(ativoDto, ativoModel);
            ativoModel.setAtivoDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
            ativoService.save(ativoModel);
            return ResponseEntity.status(HttpStatus.OK).body(ativoModel);
        }
    }
}