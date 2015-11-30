package com.powerlogic.jcompany.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * CPF constraint validation.
 */
public class CpfValidator implements ConstraintValidator<Cpf, String> {

	@Override
	public void initialize(final Cpf constraintAnnotation) {
	}

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
	
	private boolean isValido(String cpf) {
		cpf = cpf.replace(".", "").replace("-", "");
        if (cpf.length() != 11 && !StringUtils.isNumeric(cpf))
            return false;
        String numDig = cpf.substring(0, 9);
        return gerarDigitoVerificador(numDig).equals(cpf.substring(9, 11));
	}

	
	public  String gerarDigitoVerificador(String num) {
		return obterDV(num, false, 2);
	}

	public String obterDV (String fonte, boolean dezPorX, int quantidadeDigitos) {
		if (quantidadeDigitos > 1) {
			String parcial = obterDV(fonte, dezPorX);
			return parcial + obterDV(fonte + parcial, dezPorX, --quantidadeDigitos);
		} else {
			return obterDV(fonte, dezPorX);
		}
	}
	
	public String obterDV (String fonte, boolean dezPorX) {
		
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
