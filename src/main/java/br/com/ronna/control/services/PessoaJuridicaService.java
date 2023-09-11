package br.com.ronna.control.services;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.EmpresaModel;
import br.com.ronna.control.models.PessoaJuridicaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PessoaJuridicaService {

    List<PessoaJuridicaModel> findAll();

    Optional<PessoaJuridicaModel> findById(UUID clienteId);

    void delete(PessoaJuridicaModel pessoaJuridicaModel);

    void save(PessoaJuridicaModel pessoaJuridicaModel);

    boolean existsByClienteCNPJ(String clienteCNPJ);

    boolean existsByClienteInscricaoEstadual(String clienteInscricaoEstadual);

    List<PessoaJuridicaModel> findAllByEmpresaId(UUID empresaId);

    Page<PessoaJuridicaModel> findAll(Pageable pageable);

    Page<PessoaJuridicaModel> findAllByEmpresaId(EmpresaModel empresaModel, Pageable pageable);
}
