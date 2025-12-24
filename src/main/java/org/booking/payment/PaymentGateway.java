package org.booking.payment;

import org.booking.checkout.CheckoutSession;
import org.booking.order.Order;
import org.booking.checkout.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order) throws PaymentException;
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
