package com.taxi.app.domain.driver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    // Buscar motoristas ativos com paginação
    Page<Driver> findAllByAtivoTrue(Pageable paginacao);

    Optional<Driver> findFirstByStatus(StatusDriver status);

    // Consulta personalizada para obter o status 'ativo' de um motorista pelo id
    @Query("""
            select m.ativo
            from Driver m
            where m.id = :id
            """)
    Boolean findAtivoById(Long id);

}
