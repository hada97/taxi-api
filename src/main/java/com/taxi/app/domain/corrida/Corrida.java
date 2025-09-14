package com.taxi.app.domain.corrida;

import com.taxi.app.domain.user.User;
import com.taxi.app.domain.driver.Driver;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
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

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;  // duas casas decimais

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusCorrida status = StatusCorrida.INPROGRESS;

    public Corrida(User user, Driver driver, String origem, String destino, BigDecimal preco, StatusCorrida status) {
        this.user = user;
        this.driver = driver;
        this.origem = origem;
        this.destino = destino;
        this.preco = preco;
        this.status = status;
    }


}
