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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor  // Gera um construtor sem argumentos
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@Builder  // Gera o padrão de construção com o builder
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

    @Column(length = 20)
    private Status status;  // Status da corrida

    // Enum para o status da corrida
    public enum Status {
        PENDENTE,
        EM_ANDAMENTO,
        CONCLUIDA,
        CANCELADA
    }
}
