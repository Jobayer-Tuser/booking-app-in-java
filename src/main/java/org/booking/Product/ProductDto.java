package org.booking.Product;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price) {
}