package org.booking.City;

import java.math.BigDecimal;

public record CityDto(
        Long id,
        String name,
        BigDecimal latitude,
        BigDecimal longitude,
        Long countryId
) {}
