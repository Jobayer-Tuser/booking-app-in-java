package org.booking.orders;

import java.math.BigDecimal;

public record OrderProductDto(
        Long id,
        String name,
        BigDecimal price
){}
