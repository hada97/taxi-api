package com.taxi.app.domain.corrida;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import com.taxi.app.tomApi.GeocodingService;
import com.taxi.app.tomApi.TomTomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.AbstractNamedValueArgumentResolver;

import javax.sound.midi.SoundbankResource;
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

    @Autowired
    private TomTomService tomTomService;

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
        corrida.setStatus(StatusCorrida.EM_ANDAMENTO);
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


    private double calcularPreco(String origem, String destino) {

        double[] coordenadasOrigem = geocodingService.geocode(origem);
        double[] coordenadasDestino = geocodingService.geocode(destino);

        String jsonResponse = tomTomService.calcularRota(coordenadasOrigem[0], coordenadasOrigem[1], coordenadasDestino[0], coordenadasDestino[1]);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            double distanciaEmMetros = rootNode.path("routes").path(0).path("summary").path("lengthInMeters").asDouble();
            int tempoEmSegundos = rootNode.path("routes").path(0).path("summary").path("travelTimeInSeconds").asInt();

            System.out.println("Distância: " + distanciaEmMetros + " metros");
            System.out.println("Tempo de viagem: " + tempoEmSegundos + " segundos");

            double precoCalculado = 5.0 + (distanciaEmMetros * 0.002);
            System.out.println("Preco: " + precoCalculado);
            return precoCalculado;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar a resposta JSON da API TomTom: " + e.getMessage());
        }
    }

    public Object concluir(Long id) {
        var corridaOptional = corridaRepository.findById(id);
        if (corridaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corrida não encontrada.");
        }
        var corrida = corridaOptional.get();
        var driver = corrida.getDriver();
        driver.setStatus(StatusDriver.DISPONIVEL);
        corrida.setStatus(StatusCorrida.CONCLUIDA);
        driverRepository.save(driver);
        corridaRepository.save(corrida);
        return new DadosDetalharCorridas(corrida);
    }

}
