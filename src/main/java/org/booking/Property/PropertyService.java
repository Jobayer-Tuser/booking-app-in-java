package org.booking.Property;

import lombok.RequiredArgsConstructor;
import org.booking.Auth.AuthService;
import org.booking.City.CityRepository;
import org.booking.Exceptions.ResourcesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.booking.Utils.SpecificationBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService implements PropertyInterface {

    private final AuthService authService;
    private final CityRepository cityRepository;
    private final PropertyMapper propertyMapper;
    private final PropertyRepository propertyRepository;
    private final PropertySpecifications propertySpecifications;

    @Override
    public PropertyDto addNewProperty(StorePropertyRequest request) {

        var city = cityRepository.findById(request.cityId()).orElseThrow(
                () -> new ResourcesNotFoundException("City not found from this " + request.cityId()));

        var property = propertyMapper.toEntity(request);

        property.setOwner(authService.getCurentUser());
        property.setCity(city);

        var storedProperty = propertyRepository.save(property);
        return propertyMapper.toDto(storedProperty);
    }

    @Override
    public List<PropertyDto> searchProperty(PropertySearchRequest request) {

        Specification<Property> spec = new SpecificationBuilder<Property>()
                .when(request.cityId(), propertySpecifications::withCity)
                .when(request.countryId(), propertySpecifications::withCountry)
                .when(request.adults() != null && request.children() != null,
                        propertySpecifications.withCapacity(request.adults(), request.children()))
                .build();

        var properties = propertyRepository.findAll(spec);
        return propertyMapper.toSearchFilterDto(properties);
    }
}