package org.booking.Order;

import java.math.BigDecimal;

public record OrderItemDto(OrderProductDto product, int quantity, BigDecimal totalPrice) {}
