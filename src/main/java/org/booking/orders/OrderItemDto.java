package org.booking.orders;

import java.math.BigDecimal;

public record OrderItemDto(OrderProductDto product, int quantity, BigDecimal totalPrice) {}
