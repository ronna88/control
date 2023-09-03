package br.com.ronna.control.services;

import br.com.ronna.control.models.ContratoModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContratoService {

    List<ContratoModel> findAll();

    Optional<ContratoModel> findById(UUID contratoId);

    void delete(ContratoModel contratoModel);

    void save(ContratoModel contratoModel);

}
