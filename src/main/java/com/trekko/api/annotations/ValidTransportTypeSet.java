package com.trekko.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trekko.api.validators.TransportTypeSetValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = TransportTypeSetValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransportTypeSet {
    String message() default "FAILED_INVALID_TRANSPORT_TYPE_SET";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
