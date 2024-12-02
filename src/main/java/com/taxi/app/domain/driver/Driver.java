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

    @Size(min = 9, max = 9)
    @NotBlank
    @Column(unique = true)
    private String cnh;

    @Size(min = 7, max = 7)
    @NotBlank
    @Column(unique = true)
    private String placa;

    @Enumerated(EnumType.STRING)
    private StatusDriver status = StatusDriver.DISPONIVEL;

    public void setStatus(StatusDriver status) {
            this.status = status;
    }

    public void desativar() {
        this.status = StatusDriver.INATIVO;
    }

}
