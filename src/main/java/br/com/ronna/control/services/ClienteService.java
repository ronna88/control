package br.com.ronna.control.services;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.PessoaJuridicaModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteService {

    List<ClienteModel> findAll();

    Optional<ClienteModel> findById(UUID clienteId);

    void delete(ClienteModel clienteModel);

    void save(ClienteModel clienteModel);

}
