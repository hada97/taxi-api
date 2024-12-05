package com.taxi.app.domain.corrida;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import com.taxi.app.tomApi.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
    private GeocodingService geocodingService;

    public DadosDetalharCorridas marcar(DadosSolicitarCorridas dados) {

        User user = userRepository.findById(dados.idUser())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado e isso"));
        Driver driver = escolherDriver();
        var preco = calcularPreco(dados.origem(), dados.destino());
        var origem = dados.origem();
        var destino = dados.destino();
        Corrida corrida = new Corrida(
                user,
                driver,
                origem,
                destino,
                preco,
                StatusCorrida.PENDENTE
        );

        corridaRepository.save(corrida);
        // Altera o status para EM_ANDAMENTO
        corrida.setStatus(StatusCorrida.EM_ANDAMENTO);
        // Salva a corrida novamente com o novo status
        corridaRepository.save(corrida);
        driver.setStatus(StatusDriver.OCUPADO);
        driverRepository.save(driver);
        return new DadosDetalharCorridas(corrida);
    }


    private Driver escolherDriver() {
        List<Driver> motoristasDisponiveis = driverRepository.findByStatus(StatusDriver.DISPONIVEL);

        if (motoristasDisponiveis.size() > 10) {
            motoristasDisponiveis = motoristasDisponiveis.subList(0, 10);}

        if (motoristasDisponiveis.isEmpty()) {
            throw new RuntimeException("Não há motoristas disponíveis no momento.");}

        Random random = new Random();
        int r = random.nextInt(motoristasDisponiveis.size());
        return motoristasDisponiveis.get(r);
    }

    /*
    private double calcularPreco(String origem, String destino) {
        // Obter as coordenadas da origem e do destino
        double[] coordenadasOrigem = geocodingService.geocode(origem);
        double[] coordenadasDestino = geocodingService.geocode(destino);

        // Calcular a distância entre as coordenadas utilizando a fórmula de Haversine
        double distancia = calcularDistancia(coordenadasOrigem[0], coordenadasOrigem[1], coordenadasDestino[0], coordenadasDestino[1]);

        // O preço será baseado na distância. Exemplo simples: 5 reais fixos + 2 reais por km
        double precoCalculado = 5.0 + (distancia * 2.5);  // 2 reais por quilômetro

        return precoCalculado;
    }*/

    private double calcularPreco(String origem, String destino) {
        double precoCalculado = 6.0;
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

    public Object concluir(Long id) {
        var corridaOptional = corridaRepository.findById(id);
        if (corridaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corrida não encontrada.");
        }
        var corrida = corridaOptional.get();
        corrida.setStatus(StatusCorrida.CONCLUIDA);
        return new DadosDetalharCorridas(corrida);
    }

}
