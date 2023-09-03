package br.com.ronna.control.repositories;

import br.com.ronna.control.models.FuncionarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, UUID>, JpaSpecificationExecutor<FuncionarioModel> {

    boolean existsByFuncionarioCPF(String funcionarioCPF);
}
