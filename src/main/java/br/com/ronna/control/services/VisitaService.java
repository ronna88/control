package br.com.ronna.control.services;

import br.com.ronna.control.models.VisitaModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VisitaService {

    List<VisitaModel> buscaTodasVisitas();

    Optional<VisitaModel> findById(UUID visitaId);

    void save(VisitaModel visitaModel);
}
