package org.booking.properties;

import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.booking.cities.CityRepository;
import org.booking.exception.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService implements PropertyInterface {

    private final AuthService authService;
    private final CityRepository cityRepository;
    private final PropertyMapper propertyMapper;
    private final PropertyRepository propertyRepository;

    @Override
    public PropertyDto addNewProperty(StorePropertyRequest request)
    {
        var city = cityRepository.findById(request.cityId()).orElseThrow(
                () -> new ResourcesNotFoundException("City not found from this " + request.cityId())
        );

        var property = propertyMapper.toEntity(request);

        property.setOwner(authService.getCurentUser());
        property.setCity(city);

        var createProperty = propertyRepository.save(property);
        return propertyMapper.toDto(createProperty);
    }
}