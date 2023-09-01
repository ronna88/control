package br.com.ronna.control.repositories;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.EmpresaModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<EmpresaModel, UUID>, JpaSpecificationExecutor<EmpresaModel> {

    boolean existsByEmpresaCNPJ(String empresaCNPJ);
    boolean existsByEmpresaInscricaoEstadual(String empresaInscricaoEstadual);


}
