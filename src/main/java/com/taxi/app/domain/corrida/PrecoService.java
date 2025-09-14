package com.taxi.app.domain.corrida;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.driver.DriverRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PrecoService {

    private final DriverRepository driverRepository;

    public PrecoService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public BigDecimal calcularPreco(String jsonResponse) {
        try {
            double dinamico = calcularMultiplicadorDinamico();

            //double dinamico = 1.0;

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            double distanciaEmMetros = rootNode.path("routes").path(0).path("summary").path("lengthInMeters").asDouble();
            int tempoEmSegundos = rootNode.path("routes").path(0).path("summary").path("travelTimeInSeconds").asInt();

            System.out.println("Distância: " + distanciaEmMetros + " metros");
            double distanciaEmKilometros = distanciaEmMetros / 1000.0;
            System.out.println("Distância: " + distanciaEmKilometros + " km");

            BigDecimal precoCalculado = BigDecimal.valueOf((5 + distanciaEmKilometros * 2.0 * dinamico));
            System.out.println("Preço calculado: " + String.format("%.2f", precoCalculado));

            return precoCalculado;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar a resposta JSON da API TomTom: " + e.getMessage());
        }
    }

    private double calcularMultiplicadorDinamico() {
        List<Driver> motoristasDisponiveis = driverRepository.findByStatus(StatusDriver.DISP);
        int qtd = motoristasDisponiveis.size();

        if (qtd >= 7) return 1.15;
        if (qtd >= 5) return 1.2;
        if (qtd >= 3) return 1.25;
        if (qtd >= 2) return 1.3;
        return 1.5;
    }
}
