package org.booking.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.booking.Validations.ExistsId;

import java.math.BigDecimal;

public record StorePropertyRequest(

        @NotBlank(message = "properties Name is required!")
        String name,

        @NotBlank(message = "Address Street is required!")
        String addressStreet,

        @NotBlank(message = "Address Postcode is required!")
        String addressPostcode,

        BigDecimal latitude,
        BigDecimal longitude,

        @Positive
        @NotNull(message = "cities ID is required for property!")
        @ExistsId(message = "Not found any cities from provided ID")
        Long cityId
) {}
