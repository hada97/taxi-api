package com.taxi.app.domain.corrida;

import com.taxi.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {


    List<Corrida> findByStatus(StatusCorrida status);

}
