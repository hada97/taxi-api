package com.taxi.app.domain.corrida;

import com.taxi.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorridaRepository extends JpaRepository<Corrida, Long> {
}
