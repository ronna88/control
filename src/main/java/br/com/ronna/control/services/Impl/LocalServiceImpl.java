package br.com.ronna.control.services.Impl;

import br.com.ronna.control.models.LocalModel;
import br.com.ronna.control.repositories.LocalRepository;
import br.com.ronna.control.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocalServiceImpl implements LocalService {

    @Autowired
    LocalRepository localRepository;

    @Override
    public List<LocalModel> findAll() {
        return localRepository.findAll();
    }

    @Override
    public Optional<LocalModel> findById(UUID localId) {
        return localRepository.findById(localId);
    }

    @Override
    public void delete(LocalModel localModel) {
        localRepository.delete(localModel);
    }

    @Override
    public void save(LocalModel localModel) {
        localRepository.save(localModel);
    }

    @Override
    public List<LocalModel> findAllByClienteClienteId(UUID clienteId) {
        return localRepository.findAllByClienteClienteId(clienteId);
    }

}
