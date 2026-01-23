package org.booking.products;

import java.math.BigDecimal;

public record UpdateProductRequest(String productName, BigDecimal productPrice, Long categoryId) {
}
