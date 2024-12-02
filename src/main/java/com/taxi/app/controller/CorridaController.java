package com.taxi.app.controller;

import com.taxi.app.domain.corrida.Corrida;
import com.taxi.app.domain.corrida.CorridaRepository;
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

    // Criar uma nova corrida
    @PostMapping
    public ResponseEntity<Corrida> createCorrida(@RequestBody Corrida corrida) {
        Corrida savedCorrida = corridaRepository.save(corrida);
        return ResponseEntity.ok(savedCorrida);
    }

    // Listar todas as corridas
    @GetMapping
    public ResponseEntity<List<Corrida>> getAllCorridas() {
        List<Corrida> corridas = corridaRepository.findAll();
        return ResponseEntity.ok(corridas);
    }

    // Buscar uma corrida por ID
    @GetMapping("/{id}")
    public ResponseEntity<Corrida> getCorridaById(@PathVariable Long id) {
        Optional<Corrida> corrida = corridaRepository.findById(id);
        return corrida.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
