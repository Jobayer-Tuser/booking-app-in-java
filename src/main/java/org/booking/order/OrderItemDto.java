package org.booking.order;

import java.math.BigDecimal;

public record OrderItemDto(OrderProductDto product, int quantity, BigDecimal totalPrice) {}
