package com.taxi.app.tomApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TomTomService {

    @Value("${tomtom.api.key}")
    private String apiKey;

    private static final String ROUTE_API_URL = "https://api.tomtom.com/routing/1/calculateRoute/";

    private final RestTemplate restTemplate;

    public TomTomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String calcularRota(double latOrigem, double lonOrigem, double latDestino, double lonDestino) {

        String url = UriComponentsBuilder.fromHttpUrl(ROUTE_API_URL)
                .pathSegment(latOrigem + "," + lonOrigem + ":" + latDestino + "," + lonDestino)
                .path("/json")
                .queryParam("key", apiKey)  // a chave da API
                .toUriString();

        System.out.println("URL gerada: " + url);  // Imprimindo a URL gerada

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            return response.getBody();  // Retornando o JSON como String

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao calcular a rota: " + e.getMessage());
        }
    }

}
