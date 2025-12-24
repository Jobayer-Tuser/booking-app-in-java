package org.booking.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartDto(UUID id, List<CartItemDto> item, BigDecimal price) {

    public CartDto {
        item = (item == null) ? List.of() : List.copyOf(item);
        price = (price == null) ? BigDecimal.ZERO : price;
    }
}
