package br.com.ronna.control.services;

import br.com.ronna.control.models.EmpresaModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmpresaService {

    List<EmpresaModel> findAll();

    Optional<EmpresaModel> findById(UUID empresaId);

    void delete(EmpresaModel empresaModel);

    void save(EmpresaModel empresaModel);

    boolean existsByEmpresaCNPJ(String empresaCNPJ);

    boolean existsByEmpresaInscricaoEstadual(String empresaInscricaoEstadual);
}
