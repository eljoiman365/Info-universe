package com.multi_universe.info_universe.RepositorioDB;

import com.multi_universe.info_universe.Entity.FotosMarte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FotosMarteRepository extends JpaRepository<FotosMarte,Long> {

    List<FotosMarte> findAll();

    List<FotosMarte> findTop5ByOrderByIdDesc();

    @Query("SELECT f FROM FotosMarte f " + "WHERE f.earth_date = :input")
    List<FotosMarte> fotosPorFecha(String input);

    @Query("SELECT f FROM FotosMarte f " + "WHERE f.full_name = :input")
    List<FotosMarte> fotosPorNombre(String input);

    Page<FotosMarte> findById(Long id, Pageable pageable);

    Page<FotosMarte> findByName(String name, Pageable pageable);

    Boolean existsByIdConjunto(int idConjunto);

    Boolean existsByIdFoto(int idFoto);
}
