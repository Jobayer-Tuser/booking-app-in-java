package org.booking.payments;

import org.booking.orders.OrderStatus;

public record PaymentResult(Long orderId, OrderStatus paymentStatus) {

}
