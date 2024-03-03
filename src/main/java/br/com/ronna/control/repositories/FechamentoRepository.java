package br.com.ronna.control.repositories;

import br.com.ronna.control.models.FechamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FechamentoRepository extends JpaRepository<FechamentoModel, UUID>, JpaSpecificationExecutor<FechamentoModel> {
    
}