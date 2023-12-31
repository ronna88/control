package br.com.ronna.control.services;

import br.com.ronna.control.models.PessoaFisicaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PessoaFisicaService {

    List<PessoaFisicaModel> findAll();

    Optional<PessoaFisicaModel> findById(UUID clienteId);

    void delete(PessoaFisicaModel pessoaFisicaModel);

    void save(PessoaFisicaModel pessoaFisicaModel);

    boolean existsByClienteCPF(String clienteCPF);

    List<PessoaFisicaModel> findAllByEmpresaId(UUID empresaId);

    Page<PessoaFisicaModel> findAll(Pageable pageable);
}
