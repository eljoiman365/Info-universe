package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImagenTierraSateliteDTO(
        @JsonAlias("date") String date,
        @JsonAlias("id") String id,
        @JsonAlias("resource") DatosPlanetaDTO datosPlanetaDTO,
        @JsonAlias("url") String url
) {
}
