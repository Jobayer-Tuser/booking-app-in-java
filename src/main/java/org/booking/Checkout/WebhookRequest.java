package org.booking.Checkout;

import java.util.Map;

public record WebhookRequest(Map<String, String> headers, String payload) {}
