package org.booking.products;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price) {
}