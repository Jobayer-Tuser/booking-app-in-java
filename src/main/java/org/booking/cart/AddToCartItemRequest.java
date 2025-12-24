package org.booking.cart;

import jakarta.validation.constraints.NotNull;

public record AddToCartItemRequest(@NotNull(message = "Product ID is required!") Long productId) {}
