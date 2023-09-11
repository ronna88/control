package br.com.ronna.control.repositories;

import br.com.ronna.control.models.LocalModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface LocalRepository extends JpaRepository<LocalModel, UUID>, JpaSpecificationExecutor<LocalModel> {
    List<LocalModel> findAllByClienteClienteId(UUID clienteId);

    Page<LocalModel> findAllByClienteClienteId(UUID clienteId, Pageable pageable);
}
