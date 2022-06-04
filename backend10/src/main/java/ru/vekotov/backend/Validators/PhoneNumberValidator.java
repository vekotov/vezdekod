package ru.vekotov.backend.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements
        ConstraintValidator<PhoneNumberValidation, String> {
    @Override
    public void initialize(PhoneNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Long number = null;
        try {
            number = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return number >= 9_000_000_000L && number <= 9_999_999_999L;
    }
}
