package org.booking.properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertySearchTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyMapper propertyMapper;

    @InjectMocks
    private PropertyService propertyService;

    @Test
    public void testSearchProperty_WithCity() {
        // Arrange
        PropertySearchRequest request = new PropertySearchRequest(1L, null, 0, 0, null, null);
        when(propertyRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Act
        propertyService.searchProperty(request);

        // Assert
        verify(propertyRepository).findAll(any(Specification.class));
    }

    @Test
    public void testSearchProperty_WithCapacity() {
        // Arrange
        PropertySearchRequest request = new PropertySearchRequest(null, null, 2, 0, null, null);
        when(propertyRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        // Act
        propertyService.searchProperty(request);

        // Assert
        verify(propertyRepository).findAll(any(Specification.class));
    }
}
