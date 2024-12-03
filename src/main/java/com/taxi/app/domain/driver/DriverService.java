package com.taxi.app.domain.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    DriverRepository repository;

    public Page<Driver> getDriversDisponiveis(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size); // Define a página e o tamanho
        return repository.findByStatus(StatusDriver.DISPONIVEL, pageRequest);
    }
}
