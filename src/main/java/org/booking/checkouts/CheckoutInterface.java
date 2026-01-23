package org.booking.checkouts;

import org.booking.carts.Cart;
import org.booking.payments.PaymentException;

public interface CheckoutInterface {
    CheckoutResponse createOrder(Cart cart) throws PaymentException;
    void handleWebhookEvent(WebhookRequest request);
}
