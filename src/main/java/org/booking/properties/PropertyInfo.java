package org.booking.properties;

import org.booking.apartments.ApartmentInfo;

import java.math.BigDecimal;

/**
 * Projection for {@link Property}
 */
public interface PropertyInfo {
    Long getId();

    String getName();

    String getAddressStreet();

    String getAddressPostcode();

    BigDecimal getLatitude();

    BigDecimal getLongitude();

    ApartmentInfo getApartments();
}