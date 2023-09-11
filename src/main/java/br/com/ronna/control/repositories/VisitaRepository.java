package br.com.ronna.control.repositories;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.LocalModel;
import br.com.ronna.control.models.VisitaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VisitaRepository extends JpaRepository<VisitaModel, UUID>, JpaSpecificationExecutor<VisitaModel> {

    @Query(value = "select * from tb_visitas where cliente_id= :clienteId and visita_inicio >= :periodoInicio AND visita_final <= :periodoFinal", nativeQuery = true)
    List<VisitaModel> listarVisitasPorClienteEPeriodo(@Param("clienteId") UUID clienteId, @Param("periodoInicio")LocalDateTime periodoInicio, @Param("periodoFinal") LocalDateTime periodoFinal);

    @Query(value = "select * from tb_visitas where cliente_id= :clienteId and local_id= :localId and visita_inicio >= :periodoInicio AND visita_final <= :periodoFinal", nativeQuery = true)
    List<VisitaModel> listarVisitasPorClienteLocalEPeriodo(UUID clienteId, UUID localId, LocalDateTime periodoInicio, LocalDateTime periodoFinal);


    Page<VisitaModel> findVisitaModelByClienteAndVisitaInicioAfterAndVisitaFinalBefore(ClienteModel clienteModel, LocalDateTime periodoInicio, LocalDateTime periodoFinal, Pageable pageable);

    Page<VisitaModel> findVisitaModelByClienteAndLocalAndVisitaInicioAfterAndVisitaFinalBefore(ClienteModel clienteModel, LocalModel localModel, LocalDateTime periodoInicio, LocalDateTime periodoFinal, Pageable pageable);
}
