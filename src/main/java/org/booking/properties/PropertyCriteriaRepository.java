package org.booking.properties;

import java.util.List;

public interface PropertyCriteriaRepository {
    List<Property> findPropertyByCritearia(String city, String capacity, String country);
}
