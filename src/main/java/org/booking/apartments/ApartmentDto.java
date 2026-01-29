package org.booking.apartments;

import lombok.*;
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
//    private Map<String, List<String>> facilities;
}