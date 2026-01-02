package org.booking.apartments;

/**
 * Projection for {@link Apartment}
 */
public interface ApartmentInfo {
    Long getId();

    String getName();

    int getCapacityAdults();

    int getCapacityChildren();
}