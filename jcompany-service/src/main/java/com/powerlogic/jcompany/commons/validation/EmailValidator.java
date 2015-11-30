package com.powerlogic.jcompany.commons.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

	@Override
	public void initialize(final Email constraintAnnotation) {
	}

	@Override
	public boolean isValid(String email, final ConstraintValidatorContext context) {
		boolean result = false;
		if ( email == null || "".equals(email) ) {
			result = true;
		} else {
			Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");  
			Matcher matcher = pattern.matcher(email);
			result = matcher.find();
		}
		return result;
	}

}
