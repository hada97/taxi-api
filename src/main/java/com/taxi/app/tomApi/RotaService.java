package com.taxi.app.tomApi;

import org.springframework.stereotype.Service;

@Service
public class RotaService {

    private final GeocodingService geocodingService;
    private final TomTomService tomTomService;

    // Construtor para injeção de dependências
    public RotaService(GeocodingService geocodingService, TomTomService tomTomService) {
        this.geocodingService = geocodingService;
        this.tomTomService = tomTomService;
    }

    // Método para calcular a rota entre dois endereços
    public String calcularRotaEntreEnderecos(String enderecoOrigem, String enderecoDestino) {
        // Obter coordenadas (latitude e longitude) dos dois endereços
        double[][] coordenadas = geocodingService.geocodeDoisEnderecos(enderecoOrigem, enderecoDestino);

        // Calcular a rota entre as coordenadas obtidas
        return tomTomService.calcularRota(
                coordenadas[0][0], coordenadas[0][1],
                coordenadas[1][0], coordenadas[1][1]
        );
    }
}
