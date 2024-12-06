package com.taxi.app.domain.driver;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String cnh;

    @NotBlank
    private String placa;

    // Métodos adicionais podem ser adicionados conforme a necessidade, como por exemplo:
    // Conversão para Driver (se necessário)
    public Driver toEntity() {
        return Driver.builder()
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .cnh(this.cnh)
                .placa(this.placa)
                .status(StatusDriver.DISP) // Status inicial, pode ser modificado conforme necessário
                .build();
    }
}
