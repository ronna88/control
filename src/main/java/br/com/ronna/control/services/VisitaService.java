package br.com.ronna.control.services;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.LocalModel;
import br.com.ronna.control.models.VisitaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitaService {

    List<VisitaModel> buscaTodasVisitas();

    Optional<VisitaModel> findById(UUID visitaId);

    void save(VisitaModel visitaModel);

    List<VisitaModel> listarVisitasPorClienteEPeriodo(UUID clienteId, LocalDateTime periodoInicio, LocalDateTime periodoFinal);

    List<VisitaModel> listarVisitasPorClienteLocalEPeriodo(UUID clienteId, UUID localId, LocalDateTime periodoInicio, LocalDateTime periodoFinal);

    Page<VisitaModel> listarVisitasPorClienteEPeriodo(ClienteModel clienteModel, LocalDateTime periodoInicio, LocalDateTime periodoFinal, Pageable pageable);

    Page<VisitaModel> listarVisitasPorClienteLocalEPeriodo(ClienteModel clienteModel, LocalModel localModel,  LocalDateTime periodoInicio, LocalDateTime periodoFinal, Pageable pageable);

    Page<VisitaModel> findAll(Pageable pageable);
}
