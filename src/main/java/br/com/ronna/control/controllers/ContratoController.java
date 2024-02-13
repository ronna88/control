package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.ContratoDto;
import br.com.ronna.control.models.AtivoModel;
import br.com.ronna.control.models.ContratoModel;
import br.com.ronna.control.services.*;
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
    public ResponseEntity<Page<ContratoModel>> buscarTodosContratos(@PageableDefault(page = 0, size = 10, sort = "contratoId", direction = Sort.Direction.ASC)Pageable pageable){
        log.debug("Listando todos os contratos...");

        Page<ContratoModel> contratoModelPage = contratoService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(contratoModelPage);
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

        contratoModel.setContratoDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        contratoModel.setContratoDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        contratoService.save(contratoModel);
        log.debug("POST criarContrato contratoModel salvo {}", contratoModel.toString());
        log.info("Contrato criado com sucesso contratoId {}", contratoModel.getContratoId());

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
            contratoModel.setContratoDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            contratoService.save(contratoModel);
            return ResponseEntity.status(HttpStatus.OK).body(contratoModel);
        }
    }
}
