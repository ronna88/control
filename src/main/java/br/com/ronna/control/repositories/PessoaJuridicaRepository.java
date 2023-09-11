package br.com.ronna.control.repositories;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.EmpresaModel;
import br.com.ronna.control.models.PessoaJuridicaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridicaModel, UUID>, JpaSpecificationExecutor<PessoaJuridicaModel> {

    boolean existsByClienteCNPJ(String clienteCNPJ);
    boolean existsByClienteInscricaoEstadual(String clienteInscricaoEstadual);

    @Query(value = "select * from tb_clientes where empresa_empresa_id = :empresaId", nativeQuery = true)
    List<PessoaJuridicaModel> findAllPessoasJuridicasIntoEmpresa(@Param("empresaId") UUID empresaId);

    Page<PessoaJuridicaModel> findPessoaJuridicaModelsByEmpresa(EmpresaModel empresaModel, Pageable pageable);
}
