package com.example.customvalidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, Integer> {

    private static final Logger log = LoggerFactory.getLogger(PostalCodeValidator.class);
    @Override
    public boolean isValid(Integer postalCode, ConstraintValidatorContext context) {
       // log.info("Message from PostalCodeValidator isValid : {} ",postalCode);
        if (postalCode >=100000  && postalCode <= 999999) {
            return true;
        } else {
            return false;
        }
    }
}

