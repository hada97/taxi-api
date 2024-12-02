package com.taxi.app.domain.driver;

import com.taxi.app.domain.corrida.DadosSolicitarCorridas;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

@Entity
@Data
@Table(name = "drivers")  // Especifica o nome da tabela
@NoArgsConstructor  // Gera um construtor sem argumentos
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@Builder  // Gera o padrão de construção com o builder
@Getter
@Setter
public class Driver {

    @Autowired
    DriverRepository driverRepository;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "Email should be valid")
    @Column(unique = true)  // Garante que o email seja único no banco de dados
    private String email;

    @NotBlank
    private String phone;

    @Size(min = 9, max = 9)
    @NotBlank
    @Column(unique = true)  // Garante que a CNH seja única no banco de dados
    private String cnh;

    @NotBlank
    private String carro;

    @Size(min = 7, max = 7)
    @NotBlank
    @Column(unique = true)
    private String placa;

    private Boolean ativo;

    @Enumerated(EnumType.STRING) // Definindo o tipo de enum para o banco
    private StatusDriver status = StatusDriver.DISPONIVEL; // Usando o enum StatusDriver

    public void excluir() {
        this.ativo = false;
    }

    public void setStatus(StatusDriver status) {
            this.status = status;
    }



    private Driver escolherDriver(DadosSolicitarCorridas dados) {
        // Buscar todos os motoristas com o status DISPONIVEL
        List<Driver> motoristasDisponiveis = driverRepository.findByStatus(StatusDriver.DISPONIVEL);

        if (motoristasDisponiveis.isEmpty()) {
            throw new RuntimeException("Não há motoristas disponíveis no momento.");
        }

        // Escolher um motorista aleatório da lista de motoristas disponíveis
        Random random = new Random();
        int indiceAleatorio = random.nextInt(motoristasDisponiveis.size());

        // Retornar o motorista escolhido aleatoriamente
        return motoristasDisponiveis.get(indiceAleatorio);
    }

}
