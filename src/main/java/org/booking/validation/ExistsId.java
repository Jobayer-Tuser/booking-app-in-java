package org.booking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsIdValidator.class)
public @interface ExistsId {
    String message() default "ID is not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
