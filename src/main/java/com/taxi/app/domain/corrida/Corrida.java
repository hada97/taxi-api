package com.taxi.app.domain.corrida;

import com.taxi.app.domain.user.User;
import com.taxi.app.domain.driver.Driver;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "corridas")
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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @NotBlank
    private String origem;

    @NotBlank
    private String destino;

    private Double preco;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    private StatusCorrida status = StatusCorrida.PENDING;

    public Corrida(User user, Driver driver, String origem, String destino, double preco, StatusCorrida status) {
        this.user = user;
        this.driver = driver;
        this.origem = origem;
        this.destino = destino;
        this.preco = preco;
        this.status = status;
    }


}
