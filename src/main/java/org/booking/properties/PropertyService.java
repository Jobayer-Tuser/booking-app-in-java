package org.booking.properties;

import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.booking.cities.CityRepository;
import org.booking.exception.ResourcesNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.booking.utils.SpecificationBuilder;

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
                () -> new ResourcesNotFoundException("City not found from this " + request.cityId()));

        var property = propertyMapper.toEntity(request);

        property.setOwner(authService.getCurentUser());
        property.setCity(city);

        var storedProperty = propertyRepository.save(property);
        return propertyMapper.toDto(storedProperty);
    }

    @Override
    public List<PropertyDto> searchProperty(PropertySearchRequest request) {
        // Example using the new Laravel-like Query Builder
        // This is a direct replacement for the previous manual SpecificationBuilder
        // functionality, but using the fluent API.

        Specification<Property> spec = SpecificationBuilder.<Property>builder()
                .when(request.cityId(), PropertySpecifications::withCity)
                .when(request.countryId(), PropertySpecifications::withCountry)
                .when(request.adults() != null && request.children() != null,
                        PropertySpecifications.withCapacity(request.adults(), request.children()))
                .build();

        var properties = propertyRepository.findAll(spec);
        // return propertyMapper.toSearchFilterDto(properties);

        var builder = new org.booking.utils.QueryBuilder<Property>()
                .where("city.id", request.cityId())
                .where("city.country.id", request.countryId());

        if (request.adults() != null && request.adults() > 0) {
            builder.where(PropertySpecifications.withCapacity(request.adults(), request.children()));
        }

        // However, for the purpose of the demo of the requested "Laravel like query
        // builder",
        // we will stick to the dynamic field properties associated with the main
        // entity.
        // Let's assume we want to order by name as well.
        builder.orderBy("name", "asc");

        return propertyMapper.toSearchFilterDto(builder.get(propertyRepository));
    }

    public void queryByExampleClass(String productName) {
        // Demonstration of QueryBuilder usage for simple string matching
        var matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("name", "category")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(productName, matcher);
        propertyRepository.findAll((Sort) example);

        var result = new org.booking.utils.QueryBuilder<Property>()
                .where("name", "containing", productName)
                .get(propertyRepository);
    }

}