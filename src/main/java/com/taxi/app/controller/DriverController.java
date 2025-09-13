package com.taxi.app.controller;

import com.taxi.app.domain.driver.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverService service;

    @GetMapping
    @Cacheable("drivers")
    public ResponseEntity<List<Driver>> getAllUsers() {
        List<Driver> drivers = driverRepository.findAll();
        logger.info("Lista de usuários: {}", drivers);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/disponiveis")
    @Cacheable("drivers disp")
    public Page<Driver> getDriversDisponiveis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getDriversDisponiveis(page, size);
    }


    @GetMapping("/{id}")
    @Cacheable("drivers id")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        return driver.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = {"drivers", "drivers disp", "drivers id"}, allEntries = true)
    public ResponseEntity<DriverResponseDTO> createDriver(@RequestBody @Valid DriverDTO driverDTO) {
        Driver driver = driverDTO.toEntity();
        Driver savedDriver = driverRepository.save(driver);
        DriverResponseDTO responseDTO = DriverResponseDTO.fromEntity(savedDriver);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/desativar/{id}")
    @CacheEvict(value = "drivers", allEntries = true)
    @Transactional
    public ResponseEntity desativar(@PathVariable Long id) {
        var dto = service.desativar(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "drivers", allEntries = true)
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        try {
            service.deleteDriver(id);
            return ResponseEntity.noContent().build();  // Retorna 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();  // Retorna 404 Not Found
        }
    }

}
