package org.booking.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "products name is required")
        String name,

        @NotNull(message = "products Price is required")
        @Positive(message = "products Price should be greater than 0")
        BigDecimal price,

        @Positive
        Long categoryId
) {}
