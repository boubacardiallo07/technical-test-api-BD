package com.technicaltest.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * implement the interface {@link com.technicaltest.api.validator AgeRestrictions} to validate the age of a user
 * 
 * @author boubacar
 */
public class AgeValidator implements ConstraintValidator<AgeRestrictions, Date> {
    @Override
    public void initialize(AgeRestrictions constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate, today);
        return period.getYears() >= 18;
    }
}
