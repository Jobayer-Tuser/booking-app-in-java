package org.booking.properties;

public record PropertySearchRequest(
        Long cityId,
        Long countryId,
        int adults,
        int children,
        String cityName,
        String countryName
) {
}
