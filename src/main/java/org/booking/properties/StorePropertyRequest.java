package org.booking.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.booking.validation.ExistsId;

import java.math.BigDecimal;

public record StorePropertyRequest(

        @NotBlank(message = "Property Name is required!")
        String name,

        @NotBlank(message = "Address Street is required!")
        String addressStreet,

        @NotBlank(message = "Address Postcode is required!")
        String addressPostcode,

        BigDecimal latitude,
        BigDecimal longitude,

        @Positive
        @NotNull(message = "City ID is required for property!")
        @ExistsId(message = "Not found any City from provided ID")
        Long cityId
) {}
