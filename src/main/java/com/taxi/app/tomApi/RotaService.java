package com.taxi.app.tomApi;

import org.springframework.stereotype.Service;

@Service
public class RotaService {

    private final GeocodingService geocodingService;
    private final TomTomService tomTomService;

    public RotaService(GeocodingService geocodingService, TomTomService tomTomService) {
        this.geocodingService = geocodingService;
        this.tomTomService = tomTomService;
    }

    public String calcularRota(String enderecoOrigem, String enderecoDestino) {

        double[][] coordenadas = geocodingService.geocodeDoisEnderecos(enderecoOrigem, enderecoDestino);

        return tomTomService.calcularRota(
                coordenadas[0][0], coordenadas[0][1],
                coordenadas[1][0], coordenadas[1][1]
        );
    }
}
