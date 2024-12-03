package com.taxi.app.domain.corrida;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.User;
import com.taxi.app.domain.user.UserRepository;
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
        System.out.println("=====OK=======");
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
        double precoCalculado = 5.0;
        return precoCalculado;
    }

}
