package org.booking.properties;

import jakarta.validation.Valid;

import java.util.List;

public interface PropertyInterface {
    PropertyDto addNewProperty(@Valid StorePropertyRequest request);

    List<PropertyDto> searchProperty(PropertySearchRequest request);
}
