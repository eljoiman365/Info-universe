package com.multi_universe.info_universe.RestController.DTO_Controller.GET;

import com.multi_universe.info_universe.Entity.AsteroidesAvistadosPorDia;

public record GetAsteroides(
        Long id,
        Integer cantidad_asteroides_por_fecha,
        String nombre,
        String fecha_identificacion,
        String id_asteroide,
        double magnitud_asteroide,
        boolean es_peligroso
) {
    public GetAsteroides(AsteroidesAvistadosPorDia asteroidesAvistadosPorDia){
        this(asteroidesAvistadosPorDia.getId(), asteroidesAvistadosPorDia.getCantidadAsteroidesPorFecha(),asteroidesAvistadosPorDia.getName(),asteroidesAvistadosPorDia.getFecha(),
                asteroidesAvistadosPorDia.getNeo_reference_id(), asteroidesAvistadosPorDia.getAbsolute_magnitude_h(), asteroidesAvistadosPorDia.getIs_potentially_hazardous_asteroid());
    }


}
