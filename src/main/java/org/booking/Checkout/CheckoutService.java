package org.booking.Checkout;

import lombok.RequiredArgsConstructor;
import org.booking.Auth.AuthService;
import org.booking.cart.Cart;
import org.booking.cart.CartInterface;
import org.booking.Order.*;
import org.booking.Payment.PaymentException;
import org.booking.Exceptions.ResourcesNotFoundException;
import org.booking.Payment.PaymentGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService implements CheckoutInterface {

    private final AuthService authService;
    private final CartInterface cartInterface;
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    @Override
    @Transactional
    public CheckoutResponse createOrder(Cart cart)
    {
            if (cart.isEmpty()){
                throw new ResourcesNotFoundException("cart Item is empty!");
            }

            var order = Order.fromCart(cart, authService.getCurentUser());
            orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartInterface.clearCartItem(cart.getId());
            return new CheckoutResponse(order.getId(), session.checkoutUrl());

        } catch (PaymentException exception){
            orderRepository.delete(order);
            throw exception;
        }
    }

    @Override
    @Transactional
    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway.parseWebhookRequest(request)
            .ifPresent(paymentResult -> {
                var order = orderRepository.findById(paymentResult.orderId()).orElseThrow();
                order.setStatus(paymentResult.paymentStatus());
                orderRepository.save(order);
            });
    }
}
