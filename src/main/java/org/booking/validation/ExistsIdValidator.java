package org.booking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.booking.cities.CityRepository;

@RequiredArgsConstructor
public class ExistsIdValidator implements ConstraintValidator<ExistsId, Long> {

    private final CityRepository cityRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) return true;
        return cityRepository.existsById(id);
    }
}
