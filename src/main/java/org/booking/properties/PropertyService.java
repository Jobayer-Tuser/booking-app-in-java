package org.booking.properties;

import jakarta.validation.Valid;

import java.util.List;

public interface PropertyService {

    /**
     * Create new Property
     *
     * @param request property parameter
     * @return property
     */
    PropertyDto addNewProperty(@Valid StorePropertyRequest request);

    /**
     * Search property by provided criteria
     *
     * @param request criteria
     * @return list of property
     */
    List<PropertyDto> searchProperty(PropertySearchCriteria request);

    /**
     * Find the property by property ID
     *
     * @param propertyId value
     * @param request criteria
     * @return property
     */
    PropertyDto findPropertyById(Long propertyId, PropertySearchCriteria request);
}
