package org.booking.payments;

import org.booking.checkouts.CheckoutSession;
import org.booking.orders.Order;
import org.booking.checkouts.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order) throws PaymentException;
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
