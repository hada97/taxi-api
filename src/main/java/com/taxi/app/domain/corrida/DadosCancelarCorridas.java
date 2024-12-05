package com.taxi.app.domain.corrida;

import jakarta.validation.constraints.NotNull;

public record DadosCancelarCorridas(
        @NotNull
        Long idCorrida) {

}
