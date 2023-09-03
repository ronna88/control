package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.FuncionarioDto;
import br.com.ronna.control.enums.FuncionarioStatus;
import br.com.ronna.control.models.FuncionarioModel;
import br.com.ronna.control.services.FuncionarioService;
import lombok.extern.log4j.Log4j2;
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
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioModel>> buscarTodosFuncionarios(){
        log.debug("Listando todos os funcionários...");
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.findAll());
    }

    @GetMapping("/{funcionarioId}")
    public ResponseEntity<Object> buscarFuncionario(@PathVariable (value = "funcionarioId") UUID funcionarioId) {
        log.debug("Buscando funcionario UUID: {}", funcionarioId);
        Optional<FuncionarioModel> funcionarioModelOptional = funcionarioService.findById(funcionarioId);
        if(!funcionarioModelOptional.isPresent()) {
            log.info("Funcionario {} não encontrado!", funcionarioId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(funcionarioModelOptional.get());
        }
    }

    @PostMapping("/novo")
    public ResponseEntity<Object> criarFuncionario(@RequestBody FuncionarioDto funcionarioDto) {
        log.debug("Criação de funcionário...");

        FuncionarioModel funcionarioModel = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioDto, funcionarioModel);

        funcionarioModel.setFuncionarioStatus(FuncionarioStatus.ATIVO);
        funcionarioModel.setCreatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        funcionarioModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        funcionarioService.save(funcionarioModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioModel);
    }

    @PutMapping("/{funcionarioId}")
    public ResponseEntity<Object> editarFuncionario (@RequestBody FuncionarioDto funcionarioDto, @PathVariable (value = "funcionarioId") UUID funcionarioId) {
        log.debug("Edição de funcionário...");

        Optional<FuncionarioModel> funcionarioModelOptional = funcionarioService.findById(funcionarioId);
        if(!funcionarioModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado!");
        } else {
            FuncionarioModel funcionarioModel = funcionarioModelOptional.get();
            BeanUtils.copyProperties(funcionarioDto, funcionarioModel);

            funcionarioModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));
            funcionarioService.save(funcionarioModel);
        }

        FuncionarioModel funcionarioModel = new FuncionarioModel();
        BeanUtils.copyProperties(funcionarioDto, funcionarioModel);

        funcionarioModel.setFuncionarioStatus(FuncionarioStatus.ATIVO);
        funcionarioModel.setCreatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        funcionarioModel.setUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioModel);

    }

}
