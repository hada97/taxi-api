package com.taxi.app.domain.corrida;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import com.taxi.app.tomApi.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.AbstractNamedValueArgumentResolver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class CorridaService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CorridaRepository corridaRepository;

    @Autowired
    private GeocodingService geocodingService;  // Injetando o serviço de geocodificação

    public DadosDetalharCorridas marcar(DadosSolicitarCorridas dados) {

        User user = userRepository.findById(dados.idUser())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado e isso"));
        Driver driver = escolherDriver(dados);
        var preco = calcularPreco(dados.origem(), dados.destino());
        var origem = dados.origem();
        var destino = dados.destino();
        var corrida = new Corrida(
                user,
                driver,
                origem,
                destino,
                preco,
                dados.status() == StatusCorrida.PENDENTE ? StatusCorrida.EM_ANDAMENTO : dados.status()
        );

        corridaRepository.save(corrida);
        driver.setStatus(StatusDriver.OCUPADO);
        driverRepository.save(driver);
        return new DadosDetalharCorridas(corrida);
    }


    private Driver escolherDriver(DadosSolicitarCorridas dados) {
        // Buscar até 10 motoristas disponíveis
        List<Driver> motoristasDisponiveis = driverRepository.findByStatus(StatusDriver.DISPONIVEL);
        // Limitar o número de motoristas para no máximo 10
        if (motoristasDisponiveis.size() > 10) {
            motoristasDisponiveis = motoristasDisponiveis.subList(0, 10);
        }
        // Se não houver motoristas disponíveis, lançar uma exceção
        if (motoristasDisponiveis.isEmpty()) {
            throw new RuntimeException("Não há motoristas disponíveis no momento.");
        }
        // Escolher um motorista aleatório entre os motoristas disponíveis
        Random random = new Random();
        int indiceAleatorio = random.nextInt(motoristasDisponiveis.size());
        // Retornar o motorista escolhido
        return motoristasDisponiveis.get(indiceAleatorio);
    }


    private double calcularPreco(String origem, String destino) {
        // Obter as coordenadas da origem e do destino
        double[] coordenadasOrigem = geocodingService.geocode(origem);
        double[] coordenadasDestino = geocodingService.geocode(destino);

        // Calcular a distância entre as coordenadas utilizando a fórmula de Haversine
        double distancia = calcularDistancia(coordenadasOrigem[0], coordenadasOrigem[1], coordenadasDestino[0], coordenadasDestino[1]);

        // O preço será baseado na distância. Exemplo simples: 5 reais fixos + 2 reais por km
        double precoCalculado = 5.0 + (distancia * 2.5);  // 2 reais por quilômetro

        return precoCalculado;
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        // Fórmula de Haversine para calcular a distância entre dois pontos geográficos
        final double R = 6371.0; // Raio da Terra em quilômetros
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;  // Distância em quilômetros
    }

}
