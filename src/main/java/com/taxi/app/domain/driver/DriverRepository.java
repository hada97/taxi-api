package com.taxi.app.domain.driver;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Page<Driver> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select m.ativo
            from Drive m
            where
            m.id = :id
            """)
    Boolean findAtivoById(Long id);





}
