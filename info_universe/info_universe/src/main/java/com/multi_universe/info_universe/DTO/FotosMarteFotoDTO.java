package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FotosMarteFotoDTO(
        @JsonAlias("id") int idFoto,
        @JsonAlias("name") String name,
        @JsonAlias("full_name") String full_name
) {
}
