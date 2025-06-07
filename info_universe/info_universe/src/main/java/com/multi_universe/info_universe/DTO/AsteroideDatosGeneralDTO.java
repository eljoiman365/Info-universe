package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AsteroideDatosGeneralDTO(
        @JsonAlias("near_earth_objects") Map<String,List<AsteroideDTO>> near_earth_objects
){
}


