package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FotosMarteDatosDTO(
        @JsonAlias("id") int idConjunto,
        @JsonAlias("img_src") String img_src,
        @JsonAlias("earth_date") String earth_date,
        @JsonAlias("camera") FotosMarteFotoDTO camera
) {
}
