package com.technicaltest.api.validator;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This interface checks if users are adults before registering.
 * 
 * @author boubacar
 */
@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeRestrictions {
    String message() default "User should be adult";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}