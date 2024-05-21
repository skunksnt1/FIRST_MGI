package com.empresa.api.config;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SenhasValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SenhasValidadorIguais {

    String message() default "As senhas não coincidem.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
