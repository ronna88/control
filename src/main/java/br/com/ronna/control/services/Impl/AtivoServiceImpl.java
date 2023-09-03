package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.AtivoModel;
import br.com.ronna.control.repositories.AtivoRepository;
import br.com.ronna.control.services.AtivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AtivoServiceImpl implements AtivoService {

    @Autowired
    AtivoRepository ativoRepository;


    @Override
    public List<AtivoModel> findAll() {
        return ativoRepository.findAll();
    }

    @Override
    public Optional<AtivoModel> findById(UUID ativoId) {
        return ativoRepository.findById(ativoId);
    }

    @Override
    public void delete(AtivoModel ativoModel) {
        ativoRepository.delete(ativoModel);
    }

    @Override
    public void save(AtivoModel ativoModel) {
        ativoRepository.save(ativoModel);
    }

    @Override
    public List<AtivoModel> findAllByContratoContratoId(UUID contratoId) {
        return ativoRepository.findAllByContratoContratoId(contratoId);
    }
}
