package com.taxi.app.controller;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.DriverService;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverService service;

    @GetMapping
    public ResponseEntity<List<Driver>> getAllUsers() {
        List<Driver> drivers = driverRepository.findAll();
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/disponiveis")
    public Page<Driver> getDriversDisponiveis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getDriversDisponiveis(page, size);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Driver> createDriver(@RequestBody @Valid Driver driver) {
        Driver savedDriver = driverRepository.save(driver);
        return ResponseEntity.ok(savedDriver);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        return driver.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/desativar/{id}")
    @Transactional
    public ResponseEntity desativar(@PathVariable Long id) {
        var dto = service.concluir(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        try {
            service.deleteDriver(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found
        }
    }

}
