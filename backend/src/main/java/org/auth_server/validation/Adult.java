package org.auth_server.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = AdultValidator.class)
public @interface Adult {
    String message() default "Возраст должен быть 18 лет или больше";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}
