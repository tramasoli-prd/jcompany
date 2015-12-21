/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * Validação invariável para CPF. 
 * 
 * Valida CPF, considerando ok se chegou com null ou "".
 * 
 * @category Validator
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public class CpfValidator implements ConstraintValidator<Cpf, String> {

	@Override
	public void initialize(final Cpf constraintAnnotation) {
	}

	/**
	 * Verica se o CPF é Válido
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String cpf, final ConstraintValidatorContext context) {
		boolean result = false;
		if ( cpf == null || "".equals(cpf) ) {
			result = true;
		} else {
			result = isValido(cpf);
		}
		return result;
	}
	
	/**
	 * Verifica a validade do CPF
	 * @param cpf
	 * @return Boolean.TRUE se for valido e Boolean.False para os casos não válidos.
	 */
	private boolean isValido(String cpf) {
		cpf = cpf.replace(".", "").replace("-", "");
        if (cpf.length() != 11 && !StringUtils.isNumeric(cpf))
            return false;
        String numDig = cpf.substring(0, 9);
        return gerarDigitoVerificador(numDig).equals(cpf.substring(9, 11));
	}

	
	private  String gerarDigitoVerificador(String num) {
		return obterDV(num, false, 2);
	}

	private String obterDV (String fonte, boolean dezPorX, int quantidadeDigitos) {
		if (quantidadeDigitos > 1) {
			String parcial = obterDV(fonte, dezPorX);
			return parcial + obterDV(fonte + parcial, dezPorX, --quantidadeDigitos);
		} else {
			return obterDV(fonte, dezPorX);
		}
	}
	
	private String obterDV (String fonte, boolean dezPorX) {
		
		int peso = fonte.length() + 1;
		int dv = 0;
		for (int i = 0; i < fonte.length(); i++) {
			dv += Integer.parseInt(fonte.substring(i, i + 1)) * peso--;
		}
		dv = dv % 11;
		if (dv > 1) {
			return String.valueOf(11 - dv);
		} else if (dv == 1 && dezPorX) {
			return "X";
		}
		return "0";
		
	}
	

}
