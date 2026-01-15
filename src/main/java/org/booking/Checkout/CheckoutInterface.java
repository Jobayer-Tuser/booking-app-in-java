package org.booking.Checkout;

import org.booking.cart.Cart;
import org.booking.Payment.PaymentException;

public interface CheckoutInterface {
    CheckoutResponse createOrder(Cart cart) throws PaymentException;
    void handleWebhookEvent(WebhookRequest request);
}
