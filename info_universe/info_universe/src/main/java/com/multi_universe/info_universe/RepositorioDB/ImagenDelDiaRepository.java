package com.multi_universe.info_universe.RepositorioDB;

import com.multi_universe.info_universe.Entity.ImagenDelDia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagenDelDiaRepository extends JpaRepository<ImagenDelDia,Long>{

    List<ImagenDelDia>findAll();

    List<ImagenDelDia> findTop5ByOrderByFechaPublicacionDesc();

    @Query("SELECT i FROM ImagenDelDia i " + "WHERE i.fechaPublicacion = :input")
    List<ImagenDelDia> imagenPorFecha(String input);

    @Query("SELECT i FROM ImagenDelDia i " + "WHERE i.titulo = :input")
    List<ImagenDelDia> imagenPorNombre(String input);

    Page<ImagenDelDia> findById(Long id, Pageable pageable);

    Page<ImagenDelDia> findByTitulo(String titulo,Pageable pageable);
}
