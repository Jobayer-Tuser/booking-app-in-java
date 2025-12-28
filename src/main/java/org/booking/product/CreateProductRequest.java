package org.booking.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Product Price is required")
        @Positive(message = "Product Price should be greater than 0")
        BigDecimal price,

        @Positive
        Long categoryId
) {}
