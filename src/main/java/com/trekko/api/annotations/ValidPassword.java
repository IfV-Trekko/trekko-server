package com.trekko.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
// @NotBlank(message = "Password cannot be blank")
// @Size(min = 8, message = "Password must be at least 8 characters long")
public @interface ValidPassword {
  String message() default "Invalid password";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
  private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

  @Override
  public void initialize(final ValidPassword constraintAnnotation) {
  }

  @Override
  public boolean isValid(final String password, final ConstraintValidatorContext context) {
    System.out.println("Validating password: " + password);
    boolean isValid = password != null && Pattern.matches(PASSWORD_PATTERN, password);
    System.out.println("Validation result: " + isValid);
    return isValid;
  }
}
