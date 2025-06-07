package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImagenDelDiaDTO(
        @JsonAlias("title") String title,
        @JsonAlias("date") String date,
        @JsonAlias("explanation") String explanation,
        @JsonAlias("hdurl") String image
)
{
}
