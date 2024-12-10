package com.taxi.app.domain.driver;

import com.taxi.app.domain.corrida.CorridaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DriverService {

    @Autowired
    DriverRepository repository;

    @Autowired
    CorridaRepository corridaRepository;

    public Page<Driver> getDriversDisponiveis(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size); // Define a página e o tamanho
        return repository.findByStatus(StatusDriver.DISP, pageRequest);
    }

    @Transactional
    public ResponseEntity<?> desativar(Long id) {
        Driver driverEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com ID: " + id));

        driverEntity.setStatus(StatusDriver.INATIVO); // Alterando o status para INATIVO diretamente
        repository.save(driverEntity); // Salvando as alterações no banco de dados
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    @Transactional
    public ResponseEntity<?> deleteDriver(Long driverId) {
        Driver driverEntity = repository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com ID: " + driverId));

        repository.delete(driverEntity); // Deleta o motorista
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}
