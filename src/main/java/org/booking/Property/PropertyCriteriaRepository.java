package org.booking.Property;

import java.util.List;

public interface PropertyCriteriaRepository {
    List<Property> findPropertyByCritearia(String city, String capacity, String country);
}
