// DadosSolicitarCorridas.java
package com.taxi.app.domain.corrida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record DadosSolicitarCorridas(

        Long idDriver, // ID do motorista (opcional, caso queira associar a corrida a um motorista específico)

        @NotNull
        Long idUser, // ID do passageiro (obrigatório)

        @NotBlank
        String origem, // Origem da corrida (obrigatório)

        @NotBlank
        String destino, // Destino da corrida (obrigatório)

        StatusCorrida status) { // Status da corrida (opcional)
}
