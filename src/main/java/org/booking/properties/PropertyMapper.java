package org.booking.properties;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    Property toEntity(StorePropertyRequest request);
    PropertyDto toDto(Property property);
}
