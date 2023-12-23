package com.trekko.api.validators;

import com.trekko.api.annotations.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    /**
     * At least length 8
     * At least 1 digit
     * At least 1 lower case letter
     * At least 1 upper case letter
     * At least 1 special character
     * Considered special characters are: ! @ # $ & * _ - + = | : . ?
     */
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$&*_.\\-+=|:?]).{8,}$";

    @Override
    public void initialize(final ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}
