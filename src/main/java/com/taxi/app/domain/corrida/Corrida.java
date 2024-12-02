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
@AllArgsConstructor
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

    @NotBlank
    private String origem;  // Local de origem da corrida

    @NotBlank
    private String destino;  // Local de destino da corrida

    @DecimalMin(value = "5.0")
    private Double preco;

    private StatusCorrida status = StatusCorrida.PENDENTE;

    public Corrida(Object o, User user, Driver driver, String origem, String destino, double v, StatusCorrida statusCorrida) {
    }

    // Método para precificar a corrida
    public void precificarCorrida() {

        // Definindo preço base por quilômetro e por minuto (valores fictícios)
        double precoBaseKm = 5.0; // Preço por quilômetro
        double precoBaseMinuto = 2.0; // Preço por minuto
        // Aqui estamos chamando uma função que simula o cálculo de distância e tempo.
        double distanciaKm = calcularDistanciaKm(origem, destino); // Método que você pode definir para calcular a distância
        // Calculando o preço da corrida
        double precoCalculado = (distanciaKm * precoBaseKm);
        // Aplicando um fator adicional, como um multiplicador para condições especiais (trânsito, horário, etc.)
        precoCalculado = aplicarFatoresAdicionais(precoCalculado);
        // Definindo o preço final da corrida
        this.preco = precoCalculado;
    }
}
