package br.com.ronna.control.controllers;

import br.com.ronna.control.dtos.PessoaJuridicaDto;
import br.com.ronna.control.enums.ClienteStatus;
import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.PessoaJuridicaModel;
import br.com.ronna.control.services.EmpresaService;
import br.com.ronna.control.services.PessoaJuridicaService;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/cliente/pj")
public class PessoaJuridicaController {

    @Autowired
    private PessoaJuridicaService pessoaJuridicaService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<PessoaJuridicaModel>> buscarTodasClientesPJ(){
        log.debug("Listando todos clientes PJ...");
        List<PessoaJuridicaModel> listaClientePJ = pessoaJuridicaService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(listaClientePJ);
    }

    @GetMapping("empresa/{empresaId}")
    public ResponseEntity<List<PessoaJuridicaModel>> buscarTodosClientesPJPorEmpresa(@PathVariable(value = "empresaId") UUID empresaId){
        log.debug("Listando todos clientes PJ da empresa {}...", empresaId);
        List<PessoaJuridicaModel> listaClientePJ = pessoaJuridicaService.findAllByEmpresaId(empresaId);

        return ResponseEntity.status(HttpStatus.OK).body(listaClientePJ);
    }

    @GetMapping("{clienteId}")
    public ResponseEntity<Object> buscaEmpresa(@PathVariable(value = "clienteId")UUID clienteId){
        log.debug("Buscando cliente com ID: {}", clienteId);
        Optional<PessoaJuridicaModel> empresaModelOptional = pessoaJuridicaService.findById(clienteId);

        if(!empresaModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(empresaModelOptional.get());
        }
    }

    @PostMapping("/novo")
    public ResponseEntity<Object> criarClientePJ(@RequestBody PessoaJuridicaDto pessoaJuridicaDto) {
        log.debug("Criação de cliente...");

        if(pessoaJuridicaService.existsByClienteCNPJ(pessoaJuridicaDto.getClienteCNPJ())){
            log.warn("CNPJ {} já cadastrado no sistema", pessoaJuridicaDto.getClienteCNPJ());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: CNPJ já cadastrado");
        }
        if(pessoaJuridicaService.existsByClienteInscricaoEstadual(pessoaJuridicaDto.getClienteInscricaoEstadual())){
            log.warn("Inscrição Estadual {} já cadastrada no sistema", pessoaJuridicaDto.getClienteInscricaoEstadual());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro: Inscrição Estadual já cadastrada");
        }

        var clientePJModel = new PessoaJuridicaModel();
        BeanUtils.copyProperties(pessoaJuridicaDto, clientePJModel);

       var empresaModel =  empresaService.findById( pessoaJuridicaDto.getClienteEmpresa() );
       clientePJModel.setEmpresa(empresaModel.get());


        clientePJModel.setClienteStatus(ClienteStatus.ATIVO);
        clientePJModel.setClienteDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        clientePJModel.setClienteDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));
        pessoaJuridicaService.save(clientePJModel);
        log.debug("POST criarCliente clientePJModel salvo {}", clientePJModel.toString());
        log.info("Cliente criada com sucesso clienteId {}", clientePJModel.getClienteId());
        return ResponseEntity.status(HttpStatus.CREATED).body(clientePJModel);
    }

    @PutMapping("{clienteId}")
    public ResponseEntity<Object> editarEmpresa(@PathVariable( value = "clienteId") UUID clienteId, @RequestBody PessoaJuridicaDto pessoaJuridicaDto) {
        log.debug("Edição de cliente...");
        Optional<PessoaJuridicaModel> pessoaJuridicaModelOptional = pessoaJuridicaService.findById(clienteId);
        if(!pessoaJuridicaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        } else {
            var pessoaJuridicaModel = pessoaJuridicaModelOptional.get();
            BeanUtils.copyProperties(pessoaJuridicaDto, pessoaJuridicaModel);
            pessoaJuridicaModel.setClienteDataAtualizacao(LocalDateTime.now(ZoneId.of("UTC")));

            pessoaJuridicaService.save(pessoaJuridicaModel);

            log.debug("PUT editarCliente pessoaJuridicaModel salvo {}", pessoaJuridicaModel.toString());
            log.info("Cliente editada com sucesso clienteId {}", pessoaJuridicaModel.getClienteId());


            return ResponseEntity.status(HttpStatus.OK).body(pessoaJuridicaModel);
        }
    }
}
