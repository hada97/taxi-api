package com.taxi.app.domain.driver;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;  // Importação para a anotação @Column
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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

    @Size(min = 8, max = 15)
    @NotBlank
    private String phone;

    @Size(min = 9, max = 9)
    @NotBlank
    @Column(unique = true)  // Garante que a CNH seja única no banco de dados
    private String cnh;

    @NotBlank
    private String carro;

    @NotBlank
    @Column(unique = true)
    private String placa;

    private Boolean ativo;

    public void excluir() {
        this.ativo = false;
    }
}
