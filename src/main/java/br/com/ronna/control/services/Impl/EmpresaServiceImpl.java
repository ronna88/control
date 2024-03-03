package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.EmpresaModel;
import br.com.ronna.control.repositories.EmpresaRepository;
import br.com.ronna.control.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;


    @Override
    public List<EmpresaModel> findAll() {
        return empresaRepository.findAll();
    }

    @Override
    public Optional<EmpresaModel> findById(UUID empresaId) {
        return empresaRepository.findById(empresaId);
    }

    @Override
    public void delete(EmpresaModel empresaModel) {
        //TODO: ajustar para desativar em vez de deletar a informação.
        empresaRepository.delete(empresaModel);
    }

    @Override
    public void save(EmpresaModel empresaModel) {
        empresaRepository.save(empresaModel);
    }

    @Override
    public boolean existsByEmpresaCNPJ(String empresaCNPJ) {
        return empresaRepository.existsByEmpresaCNPJ(empresaCNPJ);
    }

    @Override
    public boolean existsByEmpresaInscricaoEstadual(String empresaInscricaoEstadual) {
        return empresaRepository.existsByEmpresaInscricaoEstadual(empresaInscricaoEstadual);
    }

    @Override
    public Page<EmpresaModel> findAll(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }


}
