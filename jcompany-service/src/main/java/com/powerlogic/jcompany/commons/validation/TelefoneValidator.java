package com.powerlogic.jcompany.commons.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<Telefone, String> {

	@Override
	public void initialize(final Telefone constraintAnnotation) {
	}

	@Override
	public boolean isValid(String telefone, final ConstraintValidatorContext context) {
		boolean result = false;
		if ( telefone == null || "".equals(telefone) ) {
			result = true;
		} else {
			Pattern pattern = Pattern.compile("\\(\\d{2}\\)\\d{4}-\\d{4}|\\(\\d{2}\\)\\d{5}-\\d{4}");  
			Matcher matcher = pattern.matcher(telefone);
			result = matcher.find();
		}
		return result;
	}

}
