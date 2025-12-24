package org.booking.cart;

import java.math.BigDecimal;

public record CartProductDto(Long id, String name, BigDecimal price) {
}
