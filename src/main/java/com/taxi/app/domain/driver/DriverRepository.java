package com.taxi.app.domain.driver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByStatus(StatusDriver status);

    // Método para buscar motoristas com status DISPONIVEL e retornar como uma página
    Page<Driver> findByStatus(StatusDriver status, Pageable pageable);
}
