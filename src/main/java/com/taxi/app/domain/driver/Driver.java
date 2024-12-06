package com.taxi.app.domain.driver;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@Table(name = "drivers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank
    private String phone;


    @NotBlank
    @Column(unique = true)
    private String cnh;


    @NotBlank
    @Column(unique = true)
    private String placa;

    @Enumerated(EnumType.STRING)
    private StatusDriver status = StatusDriver.DISP;

    public void desativar() {
        this.status = StatusDriver.INATIVO;
    }

}
