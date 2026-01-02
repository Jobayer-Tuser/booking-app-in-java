package org.booking.properties;

import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.booking.cities.CityRepository;
import org.booking.exception.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService implements PropertyInterface {

    private final AuthService authService;
    private final CityRepository cityRepository;
    private final PropertyMapper propertyMapper;
    private final PropertyRepository propertyRepository;

    @Override
    public PropertyDto addNewProperty(StorePropertyRequest request) {

        var city = cityRepository.findById(request.cityId()).orElseThrow(
                () -> new ResourcesNotFoundException("City not found from this " + request.cityId())
        );

        var property = propertyMapper.toEntity(request);

        property.setOwner(authService.getCurentUser());
        property.setCity(city);

        var storedProperty = propertyRepository.save(property);
        return propertyMapper.toDto(storedProperty);
    }

    @Override
    public List<PropertyDto> searchProperty(PropertySearchRequest request) {

        var property = propertyRepository.findAll();

        if (request.cityId() != null) {
            property = propertyRepository.findPropertiesByCity_Id(request.cityId());
        }

        if (request.cityId() != null) { // request.adults() && request.children()
            property = propertyRepository.findPropertiesByCity_Id(request.cityId());
//            property = propertyRepository.findPropertiesByCityAndApartmentsContains();
        }

        if (request.countryId() != null) {
            property = propertyRepository.findPropertiesByCity_Country_Id(request.countryId());
        }

        return propertyMapper.toSearchFilterDto(property);
    }
}