package com.taxi.app.domain.driver;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverResponseDTO {

    private Long id;
    private String name;
    private String placa;
    private StatusDriver status;

    // Construtor a partir de uma entidade Driver
    public static DriverResponseDTO fromEntity(Driver driver) {
        return new DriverResponseDTO(
                driver.getId(),
                driver.getName(),
                driver.getPlaca(),
                driver.getStatus()
        );
    }
}
