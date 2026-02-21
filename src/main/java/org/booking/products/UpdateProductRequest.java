package org.booking.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank String productName,
        @NotNull BigDecimal productPrice,
        @NotNull Long categoryId
) {}
