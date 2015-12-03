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
@Constraint(validatedBy = {CpfValidator.class})
public @interface Cpf {

	Class<?>[] groups() default {};

	String message() default "{com.powerlogic.jcompany.cpf}";

	Class<? extends Payload>[] payload() default {};

}