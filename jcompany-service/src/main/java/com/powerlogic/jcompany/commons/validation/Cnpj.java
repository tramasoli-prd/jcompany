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
 * Validação invariável para CNPJ. 
 * 
 * Valida dígito conforme padrão.
 * 
 * @category Annotation
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@Documented
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@Constraint(validatedBy = CnpjValidator.class)
public @interface Cnpj {

	Class<?>[] groups() default {};

	String message() default "{com.powerlogic.jcompany.cnpj}";

	Class<? extends Payload>[] payload() default {};
	
}
