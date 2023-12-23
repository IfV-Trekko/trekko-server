package com.trekko.api.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.trekko.api.annotations.ValidVehicleSet;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VehicleSetValidator implements ConstraintValidator<ValidVehicleSet, Set<String>> {
    // TODO: dont hardcode these values
    private static final Set<String> VALID_VEHICLES = new HashSet<>(Arrays.asList(
            "BYFOOT", "BICYCLE", "CAR", "PUBLIC", "SHIP", "PLANE", "OTHER"));

    @Override
    public boolean isValid(final Set<String> value, final ConstraintValidatorContext context) {
        return value != null && value.stream().allMatch(VALID_VEHICLES::contains);
    }
}
