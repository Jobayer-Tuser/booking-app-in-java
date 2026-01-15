package org.booking.Order;

import java.math.BigDecimal;

public record OrderProductDto(
        Long id,
        String name,
        BigDecimal price
){}
