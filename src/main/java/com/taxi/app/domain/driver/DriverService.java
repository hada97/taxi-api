package com.taxi.app.domain.driver;

import com.taxi.app.domain.corrida.CorridaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    DriverRepository repository;

    @Autowired
    CorridaRepository corridaRepository;

    public Page<Driver> getDriversDisponiveis(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size); // Define a página e o tamanho
        return repository.findByStatus(StatusDriver.DISPONIVEL, pageRequest);
    }

    public Object concluir(Long id) {
        Optional<Driver> driver = repository.findById(id);
        if (driver.isPresent()) {
            Driver driverEntity = driver.get();
            driverEntity.setStatus(StatusDriver.INATIVO); // Alterando o status para INATIVO diretamente
            repository.save(driverEntity); // Salvando as alterações no banco de dados
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        }
        return ResponseEntity.notFound().build();
    }


    @Transactional
    public void deleteDriver(Long driverId) {
        // Verifica se o motorista existe
        Optional<Driver> driver = repository.findById(driverId);
        if (driver.isPresent()) {
            // Exclui todas as corridas associadas ao motorista
            //corridaRepository.deleteByDriverId(driverId);
            // Agora, exclui o motorista
            repository.delete(driver.get());
        } else {
            throw new EntityNotFoundException("Motorista não encontrado com ID: " + driverId);
        }
    }
}
