package com.taxi.app.tomApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

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

        System.out.println("API Key: " + apiKey);  // Verifique o valor da chave da API

        // Construindo a URL para a API de rotas da TomTom, incluindo o sufixo '/json'
        String url = UriComponentsBuilder.fromHttpUrl(ROUTE_API_URL)
                .pathSegment(latOrigem + "," + lonOrigem + ":" + latDestino + "," + lonDestino)
                .path("/json")
                .queryParam("key", apiKey)  // a chave da API
                .toUriString();

        System.out.println("URL gerada: " + url);  // Imprimindo a URL gerada

        // Criando o corpo da requisição JSON
        String jsonBody = "{\n" +
                "  \"legs\": [\n" +
                "    {\n" +
                "      \"routeStop\": {\n" +
                "        \"entryPoints\": [\n" +
                "          {\n" +
                "            \"latitude\": " + latOrigem + ",\n" +
                "            \"longitude\": " + lonOrigem + "\n" +
                "          },\n" +
                "          {\n" +
                "            \"latitude\": " + latDestino + ",\n" +
                "            \"longitude\": " + lonDestino + "\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Definindo o tipo de conteúdo como JSON

        // Criando a requisição com corpo e cabeçalhos
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        try {
            // Enviando a requisição POST com o corpo e os cabeçalhos configurados
            ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.POST, entity, String.class);
            return response.getBody(); // Retorna a resposta da API (geralmente em formato JSON)
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao calcular a rota: " + e.getMessage());
        }
    }

}
