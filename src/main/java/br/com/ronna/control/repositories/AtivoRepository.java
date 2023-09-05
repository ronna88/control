package br.com.ronna.control.repositories;

import br.com.ronna.control.models.AtivoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface AtivoRepository extends JpaRepository<AtivoModel, UUID>, JpaSpecificationExecutor<AtivoModel> {

    //List<AtivoModel> findAllByContratoContratoId(UUID contratoId);
}
