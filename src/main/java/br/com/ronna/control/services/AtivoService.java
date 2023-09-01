package br.com.ronna.control.services;

import br.com.ronna.control.models.AtivoModel;
import br.com.ronna.control.models.EmpresaModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AtivoService {

    List<AtivoModel> findAll();

    Optional<AtivoModel> findById(UUID ativoId);

    void delete(AtivoModel ativoModel);

    void save(AtivoModel ativoModel);


}
