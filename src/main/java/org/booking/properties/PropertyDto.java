package org.booking.properties;

import lombok.*;
import org.booking.apartments.ApartmentDto;

import java.math.BigDecimal;
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
}