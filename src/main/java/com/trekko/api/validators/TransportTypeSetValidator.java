package com.trekko.api.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.trekko.api.annotations.ValidTransportTypeSet;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransportTypeSetValidator implements ConstraintValidator<ValidTransportTypeSet, Set<String>> {
    // TODO: dont hardcode these values
    private static final Set<String> VALID_TRANSPORT_TYPES = new HashSet<>(Arrays.asList(
            "BY_FOOT", "BICYCLE", "CAR", "PUBLIC_TRANSPORT", "SHIP", "PLANE", "OTHER"));

    @Override
    public boolean isValid(final Set<String> value, final ConstraintValidatorContext context) {
        return value != null && value.stream().allMatch(VALID_TRANSPORT_TYPES::contains);
    }
}
