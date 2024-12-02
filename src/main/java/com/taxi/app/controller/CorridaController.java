package com.taxi.app.controller;

import com.taxi.app.domain.corrida.Corrida;
import com.taxi.app.domain.corrida.CorridaRepository;
import com.taxi.app.domain.corrida.CorridaRequestDTO;
import com.taxi.app.domain.corrida.StatusCorrida;
import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/corridas")
public class CorridaController {

    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;


    private Driver escolherMotoristaDisponivel() {
        return driverRepository.findFirstByStatus(StatusDriver.DISPONIVEL)
                .orElseThrow(() -> new RuntimeException("Nenhum motorista disponível"));
    }

    // Listar todas as corridas
    @GetMapping
    public ResponseEntity<List<Corrida>> getAllCorridas() {
        List<Corrida> corridas = corridaRepository.findAll();
        return ResponseEntity.ok(corridas);
    }


    // Deletar uma corrida
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCorrida(@PathVariable Long id) {
        Optional<Corrida> corrida = corridaRepository.findById(id);
        if (corrida.isPresent()) {
            corridaRepository.delete(corrida.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
