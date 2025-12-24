package org.booking.cart;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(
        @NotNull(message = "Quantity is required provide null value!")
        @Min(value = 1, message = "Quantity must be greater than Zero")
        @Max(value = 1000, message = "Quantity must be less than thousands")
        Integer quantity
) {}
