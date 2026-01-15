package org.booking.Property;

import lombok.Data;
import org.booking.Apartment.ApartmentDto;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class PropertyDto {
    private Long id;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Set<ApartmentDto> apartments;
}