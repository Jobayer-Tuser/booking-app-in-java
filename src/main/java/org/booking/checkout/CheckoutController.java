package org.booking.checkout;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.cart.CartInterface;
import org.booking.order.OrderInterface;
import org.booking.payment.PaymentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CartInterface cartInterface;
    private final OrderInterface orderInterface;
    private final CheckoutInterface checkoutInterface;

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) throws PaymentException {

        var cart = cartInterface.getCartAndItems(request.cartId());
        var orderDto = checkoutInterface.createOrder(cart);

        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestHeader Map<String, String> signature, @RequestBody String payload) {
        checkoutInterface.handleWebhookEvent(new WebhookRequest(signature, payload));
    }
}
