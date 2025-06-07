package com.multi_universe.info_universe.RestController.DTO_Controller.POST;

public record PostFotosMarte(
        Long id,
        int id_conjunto_fotos,
        String imagen,
        String fecha,
        int id_foto,
        String nombre_camara,
        String titulo
) {
}
