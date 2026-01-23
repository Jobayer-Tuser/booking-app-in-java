package org.booking.properties;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.booking.apartments.ApartmentDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {
    private Long id;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Set<ApartmentDto> apartments;

    @QueryProjection
    public PropertyDto(Long id, String name, String address, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.apartments = new HashSet<>();
    }
}