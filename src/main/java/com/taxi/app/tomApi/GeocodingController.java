package com.taxi.app.tomApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeocodingController {

    @Autowired
    private final RotaService rotaService;

    // Construtor para injeção de dependência do RotaService
    public GeocodingController(RotaService rotaService) {
        this.rotaService = rotaService;
    }

    // Endpoint para calcular a rota entre dois endereços
    @GetMapping("/calcular-rota")
    public String calcularRotaEntreEnderecos(
            @RequestParam String enderecoOrigem,
            @RequestParam String enderecoDestino) {

        return rotaService.calcularRotaEntreEnderecos(enderecoOrigem, enderecoDestino);
    }
}
