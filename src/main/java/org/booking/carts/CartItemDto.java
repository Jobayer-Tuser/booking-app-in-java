package org.booking.carts;

import java.math.BigDecimal;

public record CartItemDto(CartProductDto product, Integer quantity, BigDecimal totalPrice) {
}
