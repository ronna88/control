package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.ContratoDto;
import br.com.ronna.control.models.AtivoModel;
import br.com.ronna.control.models.ContratoModel;
import br.com.ronna.control.services.*;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/contrato")
public class ContratoController {

    @Autowired
    AtivoService ativoService;

    @Autowired
    ContratoService contratoService;

    @Autowired
    ClienteService clienteService;

    @GetMapping()
    public ResponseEntity<List<ContratoModel>> buscarTodosContratos(){
        log.debug("Listando todos os contratos...");
        return ResponseEntity.status(HttpStatus.OK).body(contratoService.findAll());
    }

    @GetMapping("/{contratoId}")
    public ResponseEntity<Object> buscarContrato(@PathVariable (value = "contratoId")UUID contratoId){
        log.debug("Buscando contrato UUID: {}", contratoId);
        Optional<ContratoModel> contratoModelOptional = contratoService.findById(contratoId);
        if(!contratoModelOptional.isPresent()){
            log.info("Contrato {} não encontrado!", contratoId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato não encontrado!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(contratoModelOptional.get());
        }
    }

    @PostMapping("/novo")
    public ResponseEntity<Object> criarContrato(@RequestBody ContratoDto contratoDto) {
        log.info("Criação de contrato...");


        var contratoModel = new ContratoModel();
        contratoModel.setContratoDescricao(contratoDto.getContratoDescricao());
        contratoModel.setContratoValorRemoto(contratoDto.getContratoValorRemoto());
        contratoModel.setContratoValorVisita(contratoDto.getContratoValorVisita());

        Set<AtivoModel> ativoTemp = new HashSet<>();
        if(!contratoDto.getListaAtivos().isEmpty()) {
            contratoDto.getListaAtivos().forEach(ativoM -> {
                var ativoModelOptional = ativoService.findById(ativoM.getAtivoId());
                if(ativoModelOptional.isPresent()){
                    ativoTemp.add(ativoModelOptional.get());
                }
            });
        }

        if(!ativoTemp.isEmpty()) {
            contratoModel.setListaAtivos(ativoTemp);
        }

        if(contratoDto.getCliente() != null) {
           var clienteModel = clienteService.findById(contratoDto.getCliente());
           log.warn("Erro: " + clienteModel.get());
           if(clienteModel.isPresent()) {
               contratoModel.setCliente(clienteModel.get());
           }

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Cliente não pode estar em branco!");
        }



        contratoModel.setContratoDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        contratoModel.setContratoDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));

        contratoService.save(contratoModel);
        log.debug("POST criarContrato contratoModel salvo {}", contratoModel.toString());
        log.info("Contrato criado com sucesso contratoId {}", contratoModel.getContratoId());


        /*
        if(!contratoDto.getListaAtivos().isEmpty()) {
            contratoDto.getListaAtivos().forEach(ativoModelDto -> {
                var ativoModelOptional = ativoService.findById(ativoModelDto.getAtivoId());
                if(ativoModelOptional.isPresent()) {
                    //ativoModelOptional.get().setContrato(contratoModel);
                    ativoService.save(ativoModelOptional.get());
                }
            });
        } else {
            log.warn("Sem ativos selecionados na criação do contrato!");
        }*/

        return ResponseEntity.status(HttpStatus.CREATED).body(contratoModel);
    }

    @PutMapping("/{contratoId}")
    public ResponseEntity<Object> editarContrato(@PathVariable (value = "contratoId") UUID contratoId, @RequestBody ContratoDto contratoDto) {
        log.debug("Editar contratoId {}  ...", contratoId);

        Optional<ContratoModel> contratoModelOptional = contratoService.findById(contratoId);
        if(!contratoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contrato não encontrado!");
        } else {
            var contratoModel = contratoModelOptional.get();
            BeanUtils.copyProperties(contratoDto, contratoModel);
            contratoModel.setContratoDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
            contratoService.save(contratoModel);
            return ResponseEntity.status(HttpStatus.OK).body(contratoModel);
        }
    }
}
