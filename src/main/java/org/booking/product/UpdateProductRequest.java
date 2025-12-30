package org.booking.product;

import java.math.BigDecimal;

public record UpdateProductRequest(String productName, BigDecimal productPrice, Long categoryId) {
}
