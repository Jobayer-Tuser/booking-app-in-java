package org.booking.apartments;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ApartmentDto {

    private String name;
    private String type;
    private Integer size;
    private String beds_list;
    private Integer bathrooms;

    @QueryProjection
    public ApartmentDto(String name, String type, Integer size, String beds_list, Integer bathrooms) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.beds_list = beds_list;
        this.bathrooms = bathrooms;
    }
}