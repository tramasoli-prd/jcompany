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
 * Validação invariável para CEP. 
 * 
 * Valida CEP conforme padrão ##.###-###
 * 
 * @category Validator
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public class CepValidator implements ConstraintValidator<Cep, String> {

	@Override
	public void initialize(final Cep constraintAnnotation) {
	}

	@Override
	public boolean isValid(String cep, final ConstraintValidatorContext context) {
		boolean result = false;
		if ( cep == null || "".equals(cep) ) {
			result = true;
		} else {
			Pattern pattern = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");  
			Matcher matcher = pattern.matcher(cep);
			result = matcher.find();
		}
		return result;
	}

}
