package com.lgcns.hrm.cv.validation;

import com.lgcns.hrm.cv.annotation.EnumeratedValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author pigx
 */
public class EnumeratedValueValidator implements ConstraintValidator<EnumeratedValue, Object> {

    private String[] names;
    private int[] ordinals;

    @Override
    public void initialize(EnumeratedValue constraintAnnotation) {
        names = constraintAnnotation.names();
        ordinals = constraintAnnotation.ordinals();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value instanceof String) {
            for (String name : names) {
                if (name.equals(value)) {
                    return true;
                }
            }
        } else if (value instanceof Integer v) {
            for (int ordinal : ordinals) {
                if (ordinal == v) {
                    return true;
                }
            }
        }
        return false;
    }
}