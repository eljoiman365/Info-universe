package com.multi_universe.info_universe.RestController.DTO_Controller.GET;

import com.multi_universe.info_universe.Entity.ImagenDelDia;

public record GetImagenDelDia(
        Long id,
        String titulo,
        String fecha_publicacion,
        String contexto,
        String imagen
)
{
    public GetImagenDelDia(ImagenDelDia imagenDelDia){
        this(imagenDelDia.getId(), imagenDelDia.getTitulo(),imagenDelDia.getFechaPublicacion(), imagenDelDia.getContexto(), imagenDelDia.getImagen());
    }

}

