package org.booking.Checkout;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CheckoutRequest (
        @NotNull(message = "cart ID is required")
        UUID cartId
){}
