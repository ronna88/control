package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.EmpresaModel;
import br.com.ronna.control.models.PessoaJuridicaModel;
import br.com.ronna.control.repositories.PessoaJuridicaRepository;
import br.com.ronna.control.services.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;


    @Override
    public List<PessoaJuridicaModel> findAll() {
        return pessoaJuridicaRepository.findAll();
    }

    @Override
    public Optional<PessoaJuridicaModel> findById(UUID clienteId) {
        return pessoaJuridicaRepository.findById(clienteId);
    }

    @Override
    public void delete(PessoaJuridicaModel pessoaJuridicaModel) {
        //TODO: ajustar para desativar em vez de deletar a informação.
        pessoaJuridicaRepository.delete(pessoaJuridicaModel);
    }

    @Override
    public void save(PessoaJuridicaModel pessoaJuridicaModel) {
        pessoaJuridicaRepository.save(pessoaJuridicaModel);
    }

    @Override
    public boolean existsByClienteCNPJ(String empresaCNPJ) {
        return pessoaJuridicaRepository.existsByClienteCNPJ(empresaCNPJ);
    }

    @Override
    public boolean existsByClienteInscricaoEstadual(String clienteInscricaoEstadual) {
        return pessoaJuridicaRepository.existsByClienteInscricaoEstadual(clienteInscricaoEstadual);
    }

    @Override
    public List<PessoaJuridicaModel> findAllByEmpresaId(UUID empresaId) {
        return pessoaJuridicaRepository.findAllPessoasJuridicasIntoEmpresa(empresaId);
    }

    @Override
    public Page<PessoaJuridicaModel> findAll(Pageable pageable) {
        return pessoaJuridicaRepository.findAll(pageable);
    }

    @Override
    public Page<PessoaJuridicaModel> findAllByEmpresaId(EmpresaModel empresaModel, Pageable pageable) {
        return pessoaJuridicaRepository.findPessoaJuridicaModelsByEmpresa(empresaModel, pageable);
    }
}
