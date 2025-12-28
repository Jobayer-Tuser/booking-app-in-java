package org.booking.payment.stripe;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.booking.checkout.CheckoutSession;
import org.booking.order.Order;
import org.booking.order.OrderItem;
import org.booking.checkout.WebhookRequest;
import org.booking.order.OrderStatus;
import org.booking.payment.PaymentException;
import org.booking.payment.PaymentGateway;
import org.booking.payment.PaymentResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePayment implements PaymentGateway {

    @Value("${stripe.webhook_secret_key}")
    private String webhookSecretKey;


    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var sessionBuilder = getSessionBuilder(order);

            order.getItems().forEach(orderItem -> {
                var lineItem = createLineItem(orderItem);
                sessionBuilder.addLineItem(lineItem);
            });

            var session = Session.create(sessionBuilder.build());
            return new CheckoutSession(session.getUrl());
        }
        catch (StripeException exception){
            throw new PaymentException(exception.getMessage());
        }
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {

        try {
            var event = Webhook.constructEvent(request.headers().get("stripe-signature"), request.payload(), webhookSecretKey);

            return switch (event.getType()) {
                case "payment_intent.succeded" ->
                    Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.Paid));

                case "payment_intent.payment_failed" ->
                    Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.Failed));

                default ->
                    Optional.empty();
            };

        } catch (SignatureVerificationException exception) {
            throw new PaymentException(exception.getMessage());
        }
    }

    private Long extractOrderId(Event event)
    {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not deserialize Stripe event, check the SDK or API version!"));

        var paymentIntent = (PaymentIntent) stripeObject;
        var orderId = paymentIntent.getMetadata().get("order_id");

        return Long.valueOf(orderId);
    }


    private static SessionCreateParams.Builder getSessionBuilder(Order order) {
        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:9000/checkout-success?orderId=" + order.getId())
                .setCancelUrl("http://localhost:9000/cancel.html")
                .putMetadata("order_id", order.getId().toString());
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem orderItem) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(orderItem.getQuantity()))
                .setPriceData(createPriceData(orderItem)).build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(OrderItem orderItem) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(orderItem)).build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem orderItem) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(orderItem.getProduct().getName())
                .build();
    }
}
