package org.booking.facilityCategories;

import org.booking.facility.FacilityDto;

import java.util.List;

public record FacilityCategoryDto(String name, List<FacilityDto> facilities) {
}
