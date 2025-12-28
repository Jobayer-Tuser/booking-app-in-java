package org.booking.payment;

import org.booking.order.OrderStatus;

public record PaymentResult(Long orderId, OrderStatus paymentStatus) {

}
