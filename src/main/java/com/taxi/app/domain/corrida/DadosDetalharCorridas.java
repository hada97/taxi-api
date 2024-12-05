
package com.taxi.app.domain.corrida;


public record DadosDetalharCorridas(
        Long id,
        Long idUser,
        Long idDriver,
        String origem,
        String destino,
        Double preco,
        StatusCorrida status
) {

    public DadosDetalharCorridas(Corrida corrida) {
        this(
                corrida.getId(),
                corrida.getUser() != null ? corrida.getUser().getId() : null,
                corrida.getDriver() != null ? corrida.getDriver().getId() : null,
                corrida.getOrigem(),
                corrida.getDestino(),
                corrida.getPreco(),
                corrida.getStatus()
        );
    }
}
