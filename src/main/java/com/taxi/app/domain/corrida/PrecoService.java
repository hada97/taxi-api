package com.taxi.app.domain.corrida;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class PrecoService {
    
    public double calcularPreco(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            double distanciaEmMetros = rootNode.path("routes").path(0).path("summary").path("lengthInMeters").asDouble();
            int tempoEmSegundos = rootNode.path("routes").path(0).path("summary").path("travelTimeInSeconds").asInt();

            System.out.println("Distância: " + distanciaEmMetros + " metros");
            double distanciaEmKilometros = distanciaEmMetros / 1000.0;
            System.out.println("Distância: " + distanciaEmKilometros + " km");

            double precoCalculado = (distanciaEmKilometros * 2.0);  // Cálculo simples baseado na distância
            System.out.println("Preço calculado: " + String.format("%.2f", precoCalculado));

            return precoCalculado;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar a resposta JSON da API TomTom: " + e.getMessage());
        }
    }
}
