package com.multi_universe.info_universe.RestController.DTO_Controller.PUT;

public record PutAsteroides(
        Long id,
        String nombre,
        double magnitud_asteroide,
        boolean es_peligroso
) {
}
