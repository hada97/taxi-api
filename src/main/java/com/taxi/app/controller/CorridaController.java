package com.taxi.app.controller;

import com.taxi.app.domain.corrida.*;
import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private CorridaService service;

    @GetMapping
    public ResponseEntity<List<Corrida>> getAllCorridas() {
        List<Corrida> corridas = corridaRepository.findAll();
        return ResponseEntity.ok(corridas);
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosSolicitarCorridas dados) {
        var dto = service.marcar(dados);
        return ResponseEntity.ok(dto);
    }

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
