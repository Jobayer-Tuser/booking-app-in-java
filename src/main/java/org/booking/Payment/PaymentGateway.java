package org.booking.Payment;

import org.booking.Checkout.CheckoutSession;
import org.booking.Order.Order;
import org.booking.Checkout.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order) throws PaymentException;
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
