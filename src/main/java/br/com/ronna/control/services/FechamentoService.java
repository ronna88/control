package br.com.ronna.control.services;

import br.com.ronna.control.models.FechamentoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FechamentoService {

    List<FechamentoModel> findAll();

    Optional<FechamentoModel> findById(UUID fechamentoId);

    void delete(FechamentoModel fechamentoModel);

    void save(FechamentoModel fechamentoModel);

    // List<FechamentoModel> findAllByClienteClienteId(UUID fechamentoId);

    Page<FechamentoModel> findAllByClienteLocalId(UUID localClienteId, Pageable pageable);

    Page<FechamentoModel> findAllByClienteLocalIdAndPeriodo(UUID localClienteId, LocalDateTime dataInicio, LocalDateTime dataFinal, Pageable pageable);
}
