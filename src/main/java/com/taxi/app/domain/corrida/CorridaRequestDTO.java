package com.taxi.app.domain.corrida;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorridaRequestDTO {
    private Long usuarioId;
    private String origem;
    private String destino;
    private Double preco;

    // Getters e Setters
}
