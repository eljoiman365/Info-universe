package com.multi_universe.info_universe.RestController.DTO_Controller.POST;

public record PostAsteroides(
        Integer cantidad_asteroides_por_fecha,
        String nombre,
        String fecha_identificacion,
        String id_asteroide,
        double magnitud_asteroide,
        boolean es_peligroso
) {
}
