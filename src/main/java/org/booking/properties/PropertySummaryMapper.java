package org.booking.properties;

import lombok.AllArgsConstructor;
import org.booking.apartments.Apartment;
import org.booking.apartments.ApartmentDto;
import org.booking.rooms.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PropertySummaryMapper {

    public List<PropertyDto> summary(List<Property> properties) {
        return properties.stream()
                .map(this::propertyBuilder)
                .toList();
    }

    private PropertyDto propertyBuilder(Property property) {
        return PropertyDto.builder()
                .id(property.getId())
                .name(property.getName())
                .address(prepareAddress(property))
                .latitude(property.getLatitude())
                .longitude(property.getLongitude())
                .apartments(prepareApartment(property.getApartments()))
                .build();
    }

    private String prepareAddress(Property property) {
        return String.format("%s, %s, %s",
                property.getAddressStreet(), property.getAddressPostcode(), property.getCity().getName());
    }

    private Set<ApartmentDto> prepareApartment(Set<Apartment> apartments) {
        return apartments.stream()
                .map(this::buildApartment)
                .collect(Collectors.toSet());
    }

    private ApartmentDto buildApartment(Apartment apartment) {
        return ApartmentDto.builder()
                .name(apartment.getName())
                .type(apartment.getApartmentType().getName())
                .size(apartment.getSize())
                .beds_list(prepareBeds(apartment.getRooms()))
                .bathrooms(apartment.getBathroom())
                .build();
    }

    private String prepareBeds(List<Room> rooms) {
        Map<String, Long> bedCounts = rooms.stream()
                .flatMap(room -> room.getBeds().stream())
                .collect(Collectors.groupingBy(
                        bed -> bed.getBedType().getName().toLowerCase(),
                        Collectors.counting()
                ));

        if (bedCounts.isEmpty()){
            return "0 Beds";
        }

        var totalBeds = bedCounts.values().stream().mapToLong(Long::longValue).sum();
        String bed_lists = bedCounts.entrySet().stream()
                .map(entry -> String.format("%s %s", entry.getValue(), entry.getKey()))
                .collect(Collectors.joining(", "));

        return String.format("%s bed%s (%s)", totalBeds, (totalBeds > 1 ? "s" : "") , bed_lists);
    }

    public PropertyDto toSingleSummary(Property property) {
        return propertyBuilder(property);
    }
}
