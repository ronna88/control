package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.VisitaModel;
import br.com.ronna.control.repositories.VisitaRepository;
import br.com.ronna.control.services.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitaServiceImpl implements VisitaService {

    @Autowired
    VisitaRepository visitaRepository;

    @Override
    public List<VisitaModel> buscaTodasVisitas() {
        return visitaRepository.findAll();
    }

    @Override
    public Optional<VisitaModel> findById(UUID visitaId) {
        return visitaRepository.findById(visitaId);
    }

    @Override
    public void save(VisitaModel visitaModel) {
        visitaRepository.save(visitaModel);
    }

    @Override
    public List<VisitaModel> listarVisitasPorClienteEPeriodo(UUID clienteId, LocalDateTime periodoInicio, LocalDateTime periodoFinal) {
        return visitaRepository.listarVisitasPorClienteEPeriodo(clienteId, periodoInicio, periodoFinal);
    }

}
