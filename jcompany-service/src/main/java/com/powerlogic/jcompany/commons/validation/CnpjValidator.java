package com.powerlogic.jcompany.commons.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;


public class CnpjValidator implements ConstraintValidator<Cnpj, String> {

	@Override
	public void initialize(final Cnpj constraintAnnotation) {
	}

	@Override
	public boolean isValid(String cnpj, final ConstraintValidatorContext context) {
		boolean result = false;
		if ( cnpj == null || "".equals(cnpj) ) {
			result = true;
		} else {
			result = isValido(cnpj);
		}
		return result;
	}

	public boolean isValido(String str_cnpj) {
		str_cnpj = str_cnpj.replace(".", "").replace("/", "").replace("-", "");
		if (str_cnpj.length() != 14 && !StringUtils.isNumeric(str_cnpj))
			return false;

		String cnpj_calc = str_cnpj.substring(0, 12) + gerarDigitoVerificador(str_cnpj.substring(0, 12));
		return str_cnpj.equals(cnpj_calc);
	}	

	public String gerarDigitoVerificador(String cnpj) {
		return obterDVBase10(cnpj, false, 2);
	}

	public String obterDVBase10 (String fonte, boolean dezPorX, int quantidadeDigitos) {
		char subUm = '0';
		if (dezPorX) {
			subUm = 'X';
		}
		return obterDVBaseParametrizada(fonte, 10, '0', subUm, quantidadeDigitos);
	}

	public static String obterDVBaseParametrizada (String fonte, int base,
			char subZero, char subUm, int quantidadeDigitos) {
		if (quantidadeDigitos > 1) {
			String parcial = obterDVBaseParametrizada(fonte, base, subZero, subUm);
			return parcial + obterDVBaseParametrizada(fonte + parcial, base, subZero, subUm, --quantidadeDigitos);
		} else {
			return obterDVBaseParametrizada(fonte, base, subZero, subUm);
		}
	}

	public static String obterDVBaseParametrizada (String fonte, int base, char subZero, char subUm) {

		int peso = 2;
		int dv = 0;
		for (int i = fonte.length() - 1; i >= 0; i--) {
			dv += Integer.parseInt(fonte.substring(i, i + 1)) * peso;
			if (peso == base - 1) {
				peso = 2;
			} else {
				peso++;
			}
		}
		dv = dv % 11;
		if (dv > 1) {
			return String.valueOf(11 - dv);
		} else if (dv == 1) {
			return String.valueOf(subUm);
		}
		return String.valueOf(subZero);

	}

}
