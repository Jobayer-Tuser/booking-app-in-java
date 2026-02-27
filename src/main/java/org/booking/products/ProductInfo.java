package org.booking.products;

import java.math.BigDecimal;

/**
 * Projection for {@link Product}
 */
public record ProductInfo(
        Long id,
        String name,
        BigDecimal price
) {}