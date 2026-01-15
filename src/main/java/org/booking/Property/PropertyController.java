package org.booking.Property;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<PropertyDto>> searchProperty(PropertySearchRequest request)
    {
        List<PropertyDto> property = propertyInterface.searchProperty(request);
        return ResponseEntity.ok(property);
    }
}
