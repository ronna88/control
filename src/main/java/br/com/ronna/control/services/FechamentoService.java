package br.com.ronna.control.services;

import br.com.ronna.control.models.FechamentoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface FechamentoService {

    Page<FechamentoModel> findAll(Pageable pageable);

    Optional<FechamentoModel> findById(UUID fechamentoId);

    void save(FechamentoModel fechamentoModel);

    void delete(FechamentoModel fechamentoModel);
}