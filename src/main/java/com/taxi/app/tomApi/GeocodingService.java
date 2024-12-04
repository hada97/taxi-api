package com.taxi.app.tomApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodingService {

    @Value("${tomtom.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.tomtom.com/search/2/geocode/";

    @Autowired
    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double[] geocode(String endereco) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL + endereco + ".json")
                .queryParam("key", apiKey)
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Parse the response and extract the latitude and longitude
            return parseLatitudeLongitude(response.getBody());
        } catch (HttpClientErrorException e) {
            // Handle exceptions, e.g., invalid address or request limit reached
            throw new RuntimeException("Erro ao buscar o endereço: " + e.getMessage());
        }
    }

    private double[] parseLatitudeLongitude(String response) {
        try {
            // Usando Jackson para parsear o JSON da resposta
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode position = rootNode.path("results").get(0).path("position");
            double latitude = position.path("lat").asDouble();
            double longitude = position.path("lon").asDouble();
            return new double[]{latitude, longitude};
        } catch (Exception e) {
            throw new RuntimeException("Erro ao parsear a resposta JSON: " + e.getMessage());
        }
    }

    // Método para geocodificar dois endereços
    public double[][] geocodeDoisEnderecos(String endereco1, String endereco2) {
        double[] coordenadas1 = geocode(endereco1);
        double[] coordenadas2 = geocode(endereco2);
        return new double[][]{coordenadas1, coordenadas2};
    }
}
