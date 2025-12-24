package org.booking.payment;

public record PaymentResult(Long orderId, OrderStatus paymentStatus) {

}
