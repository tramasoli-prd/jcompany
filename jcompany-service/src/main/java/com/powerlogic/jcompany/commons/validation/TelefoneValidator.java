/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validação invariável para Telefone. 
 * 
 * Valida o valor informado, considerando o formato de telefone com DDD.
 * 
 * @category Validator
 * @since 1.0.0
 * @author Powerlogic
 *
 */
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
