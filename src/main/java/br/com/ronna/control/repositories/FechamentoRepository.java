package br.com.ronna.control.repositories;

import br.com.ronna.control.models.FechamentoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.UUID;

public interface FechamentoRepository extends JpaRepository<FechamentoModel, UUID>, JpaSpecificationExecutor<FechamentoModel> {
    
    // Page<FechamentoModel> findAllByClienteLocalId(UUID clienteLocalId, Pageable pageable);
    
    @Query(value = "select * from tb_fechamentos where local_id= :clienteLocalId", nativeQuery = true)
    Page<FechamentoModel> findAllByClienteLocalId(UUID clienteLocalId, Pageable pageable);

    @Query(value = "select * from tb_fechamentos where local_id= :clienteLocalId and fechamento_inicio>= :dataInicio AND fechamento_final <= :dataFinal")
    Page<FechamentoModel> findAllByClienteLocalIdAndPeriodo(UUID localClienteId, LocalDateTime dataInicio, LocalDateTime dataFinal, Pageable pageable);
}