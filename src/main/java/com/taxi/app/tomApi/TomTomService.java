package com.taxi.app.tomApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

@Service
public class TomTomService {

    @Value("${tomtom.api.key}")
    private String apiKey;

    private static final String ROUTE_API_URL = "https://api.tomtom.com/routing/1/calculateRoute/";

    private final RestTemplate restTemplate;

    public TomTomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para calcular a rota entre dois pontos com base nas coordenadas
    public String calcularRota(double latOrigem, double lonOrigem, double latDestino, double lonDestino) {
        // Construindo a URL para a API de rotas da TomTom
        String url = UriComponentsBuilder.fromHttpUrl(ROUTE_API_URL)
                .pathSegment(latOrigem + "," + lonOrigem)
                .pathSegment(latDestino + "," + lonDestino)
                .queryParam("key", apiKey)
                .queryParam("traffic", "false") // Exemplo de parâmetro de tráfego
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody(); // Retorna a resposta da API (geralmente em formato JSON)
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular a rota: " + e.getMessage());
        }
    }
}
