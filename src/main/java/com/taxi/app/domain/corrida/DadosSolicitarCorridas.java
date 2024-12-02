// DadosSolicitarCorridas.java
package com.taxi.app.domain.corrida;

import jakarta.validation.constraints.NotNull;

public record DadosSolicitarCorridas(

        Long idDriver, // ID do motorista (opcional, caso queira associar a corrida a um motorista específico)

        @NotNull
        Long idUser, // ID do passageiro (obrigatório)

        @NotNull
        String origem, // Origem da corrida (obrigatório)

        @NotNull
        String destino, // Destino da corrida (obrigatório)

        StatusCorrida status) { // Status da corrida (opcional)
}
