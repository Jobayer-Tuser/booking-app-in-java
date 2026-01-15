package org.booking.Property;

import jakarta.validation.constraints.Positive;

public record PropertySearchRequest(
        Long cityId,
        Long countryId,

        @Positive
        Integer adults,

        @Positive
        Integer children,

        String cityName,
        String countryName
) { }
