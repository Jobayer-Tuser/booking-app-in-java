package org.booking.properties;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.apartments.QApartment;
import org.booking.apartments.QApartmentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/properties")
public class PropertyController
{
    private final PropertyService propertyService;
    private final JPAQueryFactory factory;
    private final PropertyMapper propertyMapper;
    private final PropertySummaryMapper propertySummaryMapper;

    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(@Valid @RequestBody StorePropertyRequest request)
    {
        var propertyDto = propertyService.addNewProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PropertyDto>> searchProperty(PropertySearchRequest request)
    {
        List<PropertyDto> property = propertyService.searchProperty(request);
        return ResponseEntity.ok(property);
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<PropertyDto> findPropertyById(@PathVariable Long propertyId, PropertySearchRequest request)
    {
        PropertyDto property = propertyService.findPropertyById(propertyId, request);
        return ResponseEntity.ok(property);
    }

    @GetMapping("fetch/{propertyId}")
    public ResponseEntity<PropertyDto> findPropertyUsingQueryDSL(@PathVariable Long propertyId, PropertySearchRequest request)
    {
        QProperty property = QProperty.property;
        QApartment apartment = QApartment.apartment;

        var propertyJPAQuery = factory.selectFrom(property)
                .leftJoin(property.apartments, apartment)
                .where(property.id.eq(propertyId))
                .where(apartment.capacityAdults.goe(request.adults())
                        .or(apartment.capacityChildren.goe(request.childs())))
                .orderBy(property.id.desc())
                .fetchFirst();

        var propertyJPAQuery1 = factory.selectFrom(property)
                .leftJoin(property.apartments, apartment)
                .where(property.id.eq(propertyId))
                .where(apartment.capacityAdults.goe(request.adults())
                        .or(apartment.capacityChildren.goe(request.childs())))
                .orderBy(property.id.desc())
                .select(new QPropertyDto(property.id, property.name, property.addressPostcode, property.latitude, property.longitude))
                .fetchFirst();


//                .transform(groupBy(property.id).list(
//                        new QPropertyDto(property.id, property.name, property.addressPostcode, property.latitude, property.longitude),set(new QApartmentDto(apartment.name, apartment.apartmentType.name, apartment.size, "bath",apartment.bathroom ))
//                ));

        var property1 = propertySummaryMapper.toSingleSummary(propertyJPAQuery);
//        PropertyDto property = propertyService.findPropertyById(propertyId, request);
        return ResponseEntity.ok(propertyJPAQuery1);
    }
}
