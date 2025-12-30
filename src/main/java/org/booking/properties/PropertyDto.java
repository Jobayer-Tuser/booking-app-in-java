package org.booking.properties;

import org.booking.cities.City;

public record PropertyDto(
        Long Id,
        String name,
        String addressStreet,
        String addressPostcode,
        City city
){}