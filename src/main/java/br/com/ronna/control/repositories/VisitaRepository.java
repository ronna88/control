package br.com.ronna.control.repositories;

import br.com.ronna.control.models.VisitaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface VisitaRepository extends JpaRepository<VisitaModel, UUID>, JpaSpecificationExecutor<VisitaModel> {


}
