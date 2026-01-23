package org.booking.checkouts;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CheckoutRequest (
        @NotNull(message = "carts ID is required")
        UUID cartId
){}
