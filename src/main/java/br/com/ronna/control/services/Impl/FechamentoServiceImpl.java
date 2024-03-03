package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.FechamentoModel;
import br.com.ronna.control.repositories.FechamentoRepository;
import br.com.ronna.control.services.FechamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FechamentoServiceImpl implements FechamentoService {
    
    @Autowired
    FechamentoRepository fechamentoRepository;

    @Override
    public Page<FechamentoModel> findAll(Pageable pageable) {
        return fechamentoRepository.findAll(pageable);
    }

    @Override
    public Optional<FechamentoModel> findById(UUID fechamentoId) {
        return fechamentoRepository.findById(fechamentoId);
    }

    @Override
    public void save(FechamentoModel fechamentoModel) {
        fechamentoRepository.save(fechamentoModel);
    }

    @Override
    public void delete(FechamentoModel fechamentoModel) {
        fechamentoRepository.delete(fechamentoModel);
    }
}