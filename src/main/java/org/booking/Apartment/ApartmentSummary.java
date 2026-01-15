package org.booking.Apartment;

/**
 * Projection for {@link Apartment}
 */
public interface ApartmentSummary {
    Long getId();

    String getName();

    int getCapacityAdults();

    int getCapacityChildren();
}