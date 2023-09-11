package br.com.ronna.control.services;

import br.com.ronna.control.models.EmpresaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<EmpresaModel> findAll(Pageable pageable);
}
