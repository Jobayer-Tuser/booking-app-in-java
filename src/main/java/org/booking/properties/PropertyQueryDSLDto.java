package org.booking.properties;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.booking.apartments.ApartmentDto;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyQueryDSLDto {

    @QueryProjection
    public PropertyQueryDSLDto(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    private Long id;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Set<ApartmentDto> apartments;
}