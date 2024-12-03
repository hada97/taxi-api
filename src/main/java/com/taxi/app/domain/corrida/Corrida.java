package com.taxi.app.domain.corrida;

import com.taxi.app.domain.user.User;
import com.taxi.app.domain.driver.Driver;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Corrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;  // Relacionamento com o User (passageiro)

    @ManyToOne
    private Driver motorista;  // Relacionamento com o Driver (motorista)

    @NotBlank(message = "O campo origem não pode estar vazio")
    private String origem;

    @NotBlank(message = "O campo destino não pode estar vazio")
    private String destino;

    @DecimalMin(value = "5.0")
    private Double preco;

    private StatusCorrida status = StatusCorrida.PENDENTE;

    public Corrida(Object o, User user, Driver driver, String origem, String destino, double v, StatusCorrida statusCorrida) {
    }

}
