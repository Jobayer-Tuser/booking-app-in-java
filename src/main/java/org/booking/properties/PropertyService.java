package org.booking.properties;

import jakarta.validation.Valid;

import java.util.List;

public interface PropertyService {
    PropertyDto addNewProperty(@Valid StorePropertyRequest request);
    List<PropertyDto> searchProperty(PropertySearchCriteria request);
    PropertyDto findPropertyById(Long propertyId, PropertySearchCriteria request);
}
