package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.FechamentoModel;
import br.com.ronna.control.repositories.FechamentoRepository;
import br.com.ronna.control.services.FechamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FechamentoServiceImpl implements FechamentoService {
    
    @Autowired
    FechamentoRepository fechamentoRepository;

    @Override
    public List<FechamentoModel> findAll() {
        return fechamentoRepository.findAll();
    }

    @Override
    public Optional<FechamentoModel> findById(UUID fechamentoId) {
        return fechamentoRepository.findById(fechamentoId);
    }

    @Override
    public void delete(FechamentoModel fechamentoModel) {
        fechamentoRepository.delete(fechamentoModel);
    }

    @Override
    public void save(FechamentoModel fechamentoModel) {
        fechamentoRepository.save(fechamentoModel);
    }

    @Override
    public Page<FechamentoModel> findAllByClienteLocalId(UUID localClienteId, Pageable pageable) {
        return fechamentoRepository.findAllByClienteLocalId(localClienteId, pageable);
    }

    @Override
    public Page<FechamentoModel> findAllByClienteLocalIdAndPeriodo(UUID localClienteId, LocalDateTime dataInicio, LocalDateTime dataFinal, Pageable pageable) {
        return fechamentoRepository.findAllByClienteLocalIdAndPeriodo(localClienteId, dataInicio, dataFinal, pageable);
    }
}
