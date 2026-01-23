package org.booking.properties;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PropertyMapper {
    Property toEntity(StorePropertyRequest request);
    PropertyDto toDto(Property property);
    List<PropertyDto> toSearchFilterDto(List<Property> property);
}
