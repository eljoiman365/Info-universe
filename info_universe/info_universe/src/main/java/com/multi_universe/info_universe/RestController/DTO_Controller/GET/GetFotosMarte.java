package com.multi_universe.info_universe.RestController.DTO_Controller.GET;

import com.multi_universe.info_universe.Entity.FotosMarte;

public record GetFotosMarte(
        Long id,
        int id_conjunto_fotos,
        String imagen,
        String fecha,
        int id_foto,
        String nombre_camara,
        String titulo
) {
    public GetFotosMarte (FotosMarte fotosMarte){
        this(fotosMarte.getId(),fotosMarte.getIdConjunto(), fotosMarte.getImg_src(), fotosMarte.getEarth_date(),
                fotosMarte.getIdFoto(), fotosMarte.getFull_name(), fotosMarte.getName());
    }
}
