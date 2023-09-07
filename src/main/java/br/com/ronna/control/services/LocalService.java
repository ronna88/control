package br.com.ronna.control.services;

import br.com.ronna.control.models.LocalModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocalService {

    List<LocalModel> findAll();

    Optional<LocalModel> findById(UUID localId);

    void delete(LocalModel localModel);

    void save(LocalModel localModel);

    List<LocalModel> findAllByClienteClienteId(UUID clienteId);
}
