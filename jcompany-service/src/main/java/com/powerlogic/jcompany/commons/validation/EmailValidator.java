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
 * Validação invariável para email. 
 * 
 * Valida email, considerando se contém '@' e '.'.
 * 
 * @category Validator
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

	@Override
	public void initialize(final Email constraintAnnotation) {
	}

	/**
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
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
