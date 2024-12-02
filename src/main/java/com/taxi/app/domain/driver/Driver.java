package com.taxi.app.domain.driver;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "drivers")  // Especifica o nome da tabela
@NoArgsConstructor  // Gera um construtor sem argumentos
@AllArgsConstructor  // Gera um construtor com todos os argumentos
@Builder  // Gera o padrão de construção com o builder
public class Driver {

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

    private StatusDriver status;

    public void excluir() {
        this.ativo = false;
    }
}
