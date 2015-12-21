/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validação invariável para Telefone. 
 * 
 * Valida conteúdo conforme padrão dos números de telefone.
 * 
 * @category Annotation
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@Documented
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Telefone {

	Class<?>[] groups() default {};

	String message() default "{com.powerlogic.jcompany.telefone}";

	Class<? extends Payload>[] payload() default {};
	
}
