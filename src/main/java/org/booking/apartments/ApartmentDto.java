package org.booking.apartments;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.booking.facility.FacilityDto;
import org.booking.facilityCategories.FacilityCategoryDto;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDto {

    private String name;
    private String type;
    private Integer size;
    private String beds_list;
    private Integer bathrooms;
    private List<FacilityCategoryDto> facility_categories;
    private Map<String, List<String>> facilities;

    @QueryProjection
    public ApartmentDto(String name, String type, Integer size, String beds_list, Integer bathrooms) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.beds_list = beds_list;
        this.bathrooms = bathrooms;
    }
}