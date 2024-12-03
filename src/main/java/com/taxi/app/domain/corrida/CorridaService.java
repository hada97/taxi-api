package com.taxi.app.domain.corrida;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class CorridaService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CorridaRepository corridaRepository;

    public DadosDetalharCorridas marcar(DadosSolicitarCorridas dados) {
        var user = userRepository.getReferenceById(dados.idUser());
        var driver = escolherDriver(dados);
        var corrida = new Corrida(
                null, // ID gerado automaticamente
                user,
                driver,
                dados.origem(),
                dados.destino(),
                0.0, // O preço pode ser calculado
                dados.status() == StatusCorrida.PENDENTE ? StatusCorrida.EM_ANDAMENTO : dados.status() // Se for PENDENTE, altera para EM_ANDAMENTO
        );
        corridaRepository.save(corrida);
        driver.setStatus(StatusDriver.OCUPADO);
        driverRepository.save(driver);
        return new DadosDetalharCorridas(corrida);
    }


    private Driver escolherDriver(DadosSolicitarCorridas dados) {
        List<Driver> motoristasDisponiveis = driverRepository.findByStatus(StatusDriver.DISPONIVEL);

        if (motoristasDisponiveis.isEmpty()) {
            throw new RuntimeException("Não há motoristas disponíveis no momento.");
        }
        // Escolher um motorista aleatório da lista de motoristas disponíveis
        Random random = new Random();
        int indiceAleatorio = random.nextInt(motoristasDisponiveis.size());

        return motoristasDisponiveis.get(indiceAleatorio);
    }

}
