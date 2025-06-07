package com.multi_universe.info_universe.RepositorioDB;

import com.multi_universe.info_universe.Entity.ImagenTierraSatelital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImagenTierraSatelitalRepository extends JpaRepository<ImagenTierraSatelital,Long> {

    List<ImagenTierraSatelital> findAll();

    List<ImagenTierraSatelital> findTop5ByOrderByIdDesc();

    @Query("SELECT f FROM ImagenTierraSatelital f " + "WHERE f.date = :input")
    List<ImagenTierraSatelital> satelitePorFecha(String input);

    Page<ImagenTierraSatelital> findById(Long id, Pageable pageable);

    Boolean existsByIdImagen(String idConjunto);
}
