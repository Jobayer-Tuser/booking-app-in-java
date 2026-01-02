package org.booking.properties;

import org.booking.cities.CityDto;

public record PropertyDto(
        Long Id,
        String name,
        String addressStreet,
        String addressPostcode,
        CityDto city
){}