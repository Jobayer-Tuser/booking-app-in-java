package org.booking.properties;

import jakarta.validation.constraints.Positive;

public record PropertySearchCriteria(
        Long cityId,
        Long countryId,

        @Positive
        Integer adults,

        @Positive
        Integer childs,

        String cityName,
        String countryName
) { }
