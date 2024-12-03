
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
                // Verifique se o user não é null antes de acessar getId
                corrida.getUser() != null ? corrida.getUser().getId() : null,
                // Verifique se o motorista não é null antes de acessar getId
                corrida.getMotorista() != null ? corrida.getMotorista().getId() : null,
                corrida.getOrigem(),
                corrida.getDestino(),
                corrida.getPreco(),
                corrida.getStatus()
        );
    }
}
