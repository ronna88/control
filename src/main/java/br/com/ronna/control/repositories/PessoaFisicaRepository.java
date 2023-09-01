package br.com.ronna.control.repositories;

import br.com.ronna.control.models.PessoaFisicaModel;
import br.com.ronna.control.models.PessoaJuridicaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisicaModel, UUID>, JpaSpecificationExecutor<PessoaFisicaModel> {

    boolean existsByClienteCPF(String clienteCPF);

    @Query(value = "select * from tb_clientes where empresa_empresa_id = :empresaId", nativeQuery = true)
    List<PessoaFisicaModel> findAllPessoasFisicasIntoEmpresa(@Param("empresaId") UUID empresaId);
}
