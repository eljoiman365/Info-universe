package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosPlanetaDTO(

        @JsonAlias("dataset") String dataset,
        @JsonAlias("planet") String planet
) {
}
