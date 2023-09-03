package br.com.ronna.control.repositories;

import br.com.ronna.control.models.ContratoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ContratoRepository extends JpaRepository<ContratoModel, UUID>, JpaSpecificationExecutor<ContratoModel> {


    @Query(value = "select tb_contratos.contrato_id, tb_contratos.contrato_descricao, tb_contratos.contrato_valor_remoto, tb_contratos.contrato_valor_visita, tb_contratos.cliente_cliente_id, " +
             " tb_ativos.ativo_id, tb_ativos.ativo_descricao, tb_ativos.ativo_valor_locacao from tb_contratos" +
            " inner join tb_ativos on tb_ativos.contrato_id = tb_contratos.contrato_id", nativeQuery = true)
    List<ContratoModel> findAllWithAtivos();


}
