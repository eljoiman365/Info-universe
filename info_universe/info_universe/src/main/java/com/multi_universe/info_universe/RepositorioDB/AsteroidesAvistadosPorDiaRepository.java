package com.multi_universe.info_universe.RepositorioDB;

import com.multi_universe.info_universe.Entity.AsteroidesAvistadosPorDia;
import com.mysql.cj.log.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AsteroidesAvistadosPorDiaRepository extends JpaRepository<AsteroidesAvistadosPorDia,Long> {

    List<AsteroidesAvistadosPorDia> findAll();

    List<AsteroidesAvistadosPorDia> findTop10ByOrderByFechaDesc();

    @Query("SELECT a FROM AsteroidesAvistadosPorDia a " + "WHERE a.fecha = :input")
    List<AsteroidesAvistadosPorDia> asteroidesPorFecha(String input);

    @Query("SELECT a FROM AsteroidesAvistadosPorDia a " + "WHERE a.name = :input")
    List<AsteroidesAvistadosPorDia> asteroidesPorNombre(String input);

    Page<AsteroidesAvistadosPorDia> findById(Long id, Pageable pageable);

    Page<AsteroidesAvistadosPorDia> findByName(String name, Pageable pageable);


}
