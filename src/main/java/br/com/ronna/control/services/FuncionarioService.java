package br.com.ronna.control.services;

import br.com.ronna.control.models.FuncionarioModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FuncionarioService {

    List<FuncionarioModel> findAll();

    Optional<FuncionarioModel> findById(UUID funcionarioId);

    boolean existsByFuncionarioCPF(String funcionarioCPF);

    void save(FuncionarioModel funcionarioModel);

    void delete(FuncionarioModel funcionarioModel);


}
