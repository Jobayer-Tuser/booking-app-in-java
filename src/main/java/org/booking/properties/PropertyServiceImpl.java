package org.booking.properties;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.booking.cities.CityRepository;
import org.booking.exceptions.ResourcesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.booking.Utils.SpecificationBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final AuthService authService;
    private final CityRepository cityRepository;
    private final PropertyMapper propertyMapper;
    private final PropertySummaryMapper propertySummaryMapper;
    private final PropertyRepository propertyRepository;
    private final PropertySpecifications propertySpecifications;
    private final JPAQueryFactory factory;

    @Override
    public PropertyDto addNewProperty(StorePropertyRequest request) {

        var city = cityRepository.findById(request.cityId()).orElseThrow(
                () -> new ResourcesNotFoundException(String.format("cities not found from this ID: %s ", request.cityId())));

        var property = propertyMapper.toEntity(request);

        property.setOwner(authService.getCurrentUser());
        property.setCity(city);

        var storedProperty = propertyRepository.save(property);
        return propertyMapper.toDto(storedProperty);
    }

    @Override
    public List<PropertyDto> searchProperty(PropertySearchRequest request) {

        Specification<Property> spec = new SpecificationBuilder<Property>()
                .when(request.cityId(), propertySpecifications::withCity)
                .when(request.countryId(), propertySpecifications::withCountry)
                .when(request.adults() != null || request.childs() != null,
                        propertySpecifications.withCapacity(request.adults(), request.childs()))
                .build();

        var properties = propertyRepository.findAll(spec);
        return propertySummaryMapper.summary(properties);
    }

    @Override
    public PropertyDto findPropertyById(Long propertyId, PropertySearchRequest request) {

        var specs = new SpecificationBuilder<Property>()
                .whereId(propertyId, propertySpecifications::findPropertyById)
                .when(request.adults() != null || request.childs() != null,
                        propertySpecifications.withCapacity(request.adults(), request.childs()))
                .when(true, propertySpecifications.orderByCapacity())
                .build();

        return propertyRepository.findOne(specs)
                .map(propertySummaryMapper::toSingleSummary)
                .orElseThrow(() -> new ResourcesNotFoundException(String.format("We could not found any property with Id %s", propertyId)));
    }
}