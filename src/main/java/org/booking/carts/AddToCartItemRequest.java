package org.booking.carts;

import jakarta.validation.constraints.NotNull;

public record AddToCartItemRequest(@NotNull(message = "products ID is required!") Long productId) {}
