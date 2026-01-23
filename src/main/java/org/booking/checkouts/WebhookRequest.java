package org.booking.checkouts;

import java.util.Map;

public record WebhookRequest(Map<String, String> headers, String payload) {}
