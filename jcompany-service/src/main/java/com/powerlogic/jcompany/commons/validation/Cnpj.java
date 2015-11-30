package com.powerlogic.jcompany.commons.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = CnpjValidator.class)
public @interface Cnpj {

	Class<?>[] groups() default {};

	String message() default "{com.powerlogic.jcompany.cnpj}";

	Class<? extends Payload>[] payload() default {};
	
}
