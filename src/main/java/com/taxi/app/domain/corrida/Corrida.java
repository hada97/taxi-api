package com.taxi.app.domain.corrida;

import com.taxi.app.domain.user.User;
import com.taxi.app.domain.driver.Driver;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Entity
@Data
@NoArgsConstructor  // Gera um construtor sem argumentos
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@Builder  // Gera o padrão de construção com o builder
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

    @NotBlank
    private String origem;  // Local de origem da corrida

    @NotBlank
    private String destino;  // Local de destino da corrida

    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private Double preco;  // Preço da corrida

    private StatusCorrida status;  // Status da corrida

}
