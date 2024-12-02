
package com.taxi.app.domain.corrida;

import com.taxi.app.domain.driver.Driver;
import com.taxi.app.domain.driver.DriverRepository;
import com.taxi.app.domain.driver.StatusDriver;
import com.taxi.app.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SolicitarCorridas {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CorridaRepository corridaRepository;

    public DadosDetalharCorridas marcar(DadosSolicitarCorridas dados) {
        var user = userRepository.getReferenceById(dados.idUser());
        var driver = escolherDriver(dados); // Método para selecionar o motorista

        var corrida = new Corrida(
                null, // ID será gerado automaticamente
                user,
                driver,
                dados.origem(),
                dados.destino(),
                0.0, // O preço pode ser calculado
                dados.status() == StatusCorrida.PENDENTE ? StatusCorrida.EM_ANDAMENTO : dados.status() // Se for PENDENTE, altera para EM_ANDAMENTO
        );
        corridaRepository.save(corrida);
        driver.setStatus(StatusDriver.OCUPADO); // Alterando o status para OCUPADO
        driverRepository.save(driver); // Salvando  no banco de dados

        return new DadosDetalharCorridas(corrida);
    }

    private Driver escolherDriver(DadosSolicitarCorridas dados) {
        return driverRepository.getReferenceById(dados.idDriver());
    }
}
