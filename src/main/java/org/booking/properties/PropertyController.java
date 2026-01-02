package org.booking.properties;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/properties")
public class PropertyController
{
    private final PropertyInterface propertyInterface;

    @PostMapping
    public ResponseEntity<PropertyDto> createProperty(@Valid @RequestBody StorePropertyRequest request)
    {
        var propertyDto = propertyInterface.addNewProperty(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyDto);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProperty(PropertySearchRequest request)
    {
        var property = propertyInterface.searchProperty(request);
        return ResponseEntity.ok(property);
    }
}
