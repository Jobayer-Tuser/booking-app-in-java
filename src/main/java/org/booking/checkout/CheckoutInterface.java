package org.booking.checkout;

import org.booking.cart.Cart;
import org.booking.payment.PaymentException;

public interface CheckoutInterface {
    CheckoutResponse createOrder(Cart cart) throws PaymentException;
    void handleWebhookEvent(WebhookRequest request);
}
