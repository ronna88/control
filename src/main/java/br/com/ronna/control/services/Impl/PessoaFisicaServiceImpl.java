package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.PessoaFisicaModel;
import br.com.ronna.control.models.PessoaJuridicaModel;
import br.com.ronna.control.repositories.PessoaFisicaRepository;
import br.com.ronna.control.repositories.PessoaJuridicaRepository;
import br.com.ronna.control.services.PessoaFisicaService;
import br.com.ronna.control.services.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;


    @Override
    public List<PessoaFisicaModel> findAll() {
        return pessoaFisicaRepository.findAll();
    }

    @Override
    public Optional<PessoaFisicaModel> findById(UUID clienteId) {
        return pessoaFisicaRepository.findById(clienteId);
    }

    @Override
    public void delete(PessoaFisicaModel pessoaFisicaModel) {
        //TODO: ajustar para desativar em vez de deletar a informação.
        pessoaFisicaRepository.delete(pessoaFisicaModel);
    }

    @Override
    public void save(PessoaFisicaModel pessoaFisicaModel) {
        pessoaFisicaRepository.save(pessoaFisicaModel);
    }

    @Override
    public boolean existsByClienteCPF(String clienteCPF) {
        return pessoaFisicaRepository.existsByClienteCPF(clienteCPF);
    }

    @Override
    public List<PessoaFisicaModel> findAllByEmpresaId(UUID empresaId) {
        return pessoaFisicaRepository.findAllPessoasFisicasIntoEmpresa(empresaId);
    }

}
