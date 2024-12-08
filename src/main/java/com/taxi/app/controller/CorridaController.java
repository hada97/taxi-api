package com.taxi.app.controller;

import com.taxi.app.domain.corrida.*;
import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("corridas")
    public ResponseEntity<List<Corrida>> getAllCorridas() {
        List<Corrida> corridas = corridaRepository.findAll();
        return ResponseEntity.ok(corridas);
    }

    @GetMapping("/andamento")
    @Cacheable("corridas in progress")
    public ResponseEntity<List<Corrida>> getCorridasEmAndamento() {
        List<Corrida> corridas = corridaRepository.findByStatus(StatusCorrida.INPROGRESS);
        return ResponseEntity.ok(corridas);
    }

    @GetMapping("/concluidas")
    @Cacheable("corridas concluidas")
    public ResponseEntity<List<Corrida>> getCorridasConcluidas() {
        List<Corrida> corridas = corridaRepository.findByStatus(StatusCorrida.COMPLETED);
        return ResponseEntity.ok(corridas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corrida> getCorridaById(@PathVariable Long id) {
        Optional<Corrida> corrida = corridaRepository.findById(id);
        if (corrida.isPresent()) {
            return ResponseEntity.ok(corrida.get());
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar a corrida
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = {"corridas", "corridas in progress", "corridas concluidas", "drivers", "drivers disp"}, allEntries = true)
    public ResponseEntity marcar(@RequestBody @Valid DadosSolicitarCorridas dados) {
        var dto = service.marcar(dados);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/concluir/{id}")
    @Transactional
    @CacheEvict(value = {"corridas", "corridas in progress", "corridas concluidas", "drivers", "drivers disp"}, allEntries = true)
    public ResponseEntity concluir(@PathVariable Long id) {
        var dto = service.concluir(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = {"corridas", "corridas in progress", "corridas concluidas", "drivers", "drivers disp"}, allEntries = true)
    public ResponseEntity<Void> deleteCorrida(@PathVariable Long id) {
        Optional<Corrida> corridaOpt = corridaRepository.findById(id);

        if (corridaOpt.isPresent()) {
            Corrida corrida = corridaOpt.get();

            Driver driver = corrida.getDriver();  // Verifique se esse método é válido no seu modelo

            if (driver != null) {
                driver.setStatus(StatusDriver.DISP); // Ou o status correto
                driverRepository.save(driver); // Salva as mudanças no driver
            }
            corridaRepository.delete(corrida);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

}
