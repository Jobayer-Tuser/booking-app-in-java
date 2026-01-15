package org.booking.Payment;

import org.booking.Order.OrderStatus;

public record PaymentResult(Long orderId, OrderStatus paymentStatus) {

}
