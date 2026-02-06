package org.booking.Validations;

import org.booking.enums.FileExtension;
import org.booking.enums.MimeTypes;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({ FIELD, METHOD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface File {
    String message() default "{constraints.File.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    MimeTypes[] mimeTypes() default {};

    FileExtension[] extensions() default {};

    long maxSize() default Long.MAX_VALUE;
}
