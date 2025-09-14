package com.taxi.app.domain.corrida;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
import com.taxi.app.tomApi.GeocodingService;
import com.taxi.app.tomApi.TomTomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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

    @Autowired
    PrecoService precoService;

    public DadosDetalharCorridas criarCorrida(DadosSolicitarCorridas dados) {

        User user = userRepository.findById(dados.idUser())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado e isso"));

        Driver driver = escolherDriver();

        var origem = dados.origem();
        var destino = dados.destino();

        double[] coordenadasOrigem = geocodingService.geocode(origem);
        double[] coordenadasDestino = geocodingService.geocode(destino);

        String jsonResponse = tomTomService.calcularRota(coordenadasOrigem[0], coordenadasOrigem[1], coordenadasDestino[0], coordenadasDestino[1]);

        var preco = precoService.calcularPreco(jsonResponse);

        Corrida corrida = new Corrida(
                user,
                driver,
                origem,
                destino,
                preco,
                StatusCorrida.PENDING
        );

        corrida.setStatus(StatusCorrida.INPROGRESS);
        driver.setStatus(StatusDriver.OCUP);
        corridaRepository.save(corrida);
        driverRepository.save(driver);
        return new DadosDetalharCorridas(corrida);
    }

    private Driver escolherDriver() {
        List<Driver> motoristasDisponiveis = driverRepository.findByStatus(StatusDriver.DISP);

        if (motoristasDisponiveis.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Nenhum motorista disponível no momento.");
        }
        if (motoristasDisponiveis.size() > 10) {
            motoristasDisponiveis = motoristasDisponiveis.subList(0, 10);
        }
        Random random = new Random();
        int r = random.nextInt(motoristasDisponiveis.size());
        return motoristasDisponiveis.get(r);
    }


    public Object encerrarCorrida(Long id) {
        var corridaOptional = corridaRepository.findById(id);
        if (corridaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corrida não encontrada.");
        }
        var corrida = corridaOptional.get();
        var driver = corrida.getDriver();
        driver.setStatus(StatusDriver.DISP);
        corrida.setStatus(StatusCorrida.COMPLETED);
        driverRepository.save(driver);
        corridaRepository.save(corrida);
        return new DadosDetalharCorridas(corrida);
    }

}
