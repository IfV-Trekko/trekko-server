package com.trekko.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.trekko.api.validators.VehicleSetValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = VehicleSetValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVehicleSet {
    String message() default "FAILED_INVALID_VEHICLE_SET";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
