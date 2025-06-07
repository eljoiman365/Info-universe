package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FotosMarteDTO(
        @JsonAlias("photos")List<FotosMarteDatosDTO> photos
        ) {
}
