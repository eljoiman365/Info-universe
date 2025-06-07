package com.multi_universe.info_universe.DTO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public record AsteroideDTO(
        @JsonAlias("neo_reference_id") String neo_reference_id,
        @JsonAlias("name") String name,
        @JsonAlias("absolute_magnitude_h") double absolute_magnitude_h,
        @JsonAlias("is_potentially_hazardous_asteroid") Boolean is_potentially_hazardous_asteroid
) {
}
