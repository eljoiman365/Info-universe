package com.multi_universe.info_universe.RestController.DTO_Controller.GET;

import com.multi_universe.info_universe.Entity.ImagenTierraSatelital;
import jakarta.persistence.Column;

public record GetImagenTierraSatelital(
         Long id,
         String fecha,
         String idImagen,
         String urlImagen,
         String satelite,
         String planeta
) {
    public GetImagenTierraSatelital(ImagenTierraSatelital satelite){
        this(satelite.getId(),satelite.getDate(),
                satelite.getIdImagen(),satelite.getUrl(),
                satelite.getDataset(),satelite.getPlanet());
    }
}
