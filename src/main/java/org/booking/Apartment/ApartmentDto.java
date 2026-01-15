package org.booking.Apartment;

import org.booking.ApartmentTypes.ApartmentTypeDto;
import org.booking.Room.RoomDto;

import java.util.Set;

public record ApartmentDto(
        Long id,
        Long apartmentTypeId,
        Long propertyId,
        String name,
        int capacityAdults,
        int capacityChildren,
        int size,
        ApartmentTypeDto apartmentType,
        Set<RoomDto> rooms

) {}