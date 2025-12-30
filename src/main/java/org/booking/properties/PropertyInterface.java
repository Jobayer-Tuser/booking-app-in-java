package org.booking.properties;

import jakarta.validation.Valid;

public interface PropertyInterface {
    PropertyDto addNewProperty(@Valid StorePropertyRequest request);
}
