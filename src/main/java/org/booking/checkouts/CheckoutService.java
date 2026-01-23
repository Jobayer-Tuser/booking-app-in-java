package org.booking.checkouts;

import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.booking.carts.Cart;
import org.booking.carts.CartInterface;
import org.booking.orders.*;
import org.booking.payments.PaymentException;
import org.booking.exceptions.ResourcesNotFoundException;
import org.booking.payments.PaymentGateway;
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
                throw new ResourcesNotFoundException("carts Item is empty!");
            }

            var order = Order.fromCart(cart, authService.getCurrentUser());
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
