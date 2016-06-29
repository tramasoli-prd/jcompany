/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br 
*/

package com.powerlogic.jcompany.commons.interceptor.validation;

import com.powerlogic.jcompany.commons.util.validation.PlcValidationInvariantUtil;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Interceptor para realizar a validação dos dados enviados através do Formulário.
 * 
 * Após criar a entidade as validações do Bean Validation são verificadas.
 * 
 * A verificação das propriedades da entidade quando ocorrerem, disparam exceção para o Front-End.
 * 
 * @category Interceptor
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public class PlcValidationInterceptor  {

	@Inject
	private PlcValidationInvariantUtil validationUtil;


	/**
	 *
	 * Retorna as mensagens de validação invariante dos campos, fazendo a troca dos tokens pelo nome da propriedade (valores reais).
	 *
	 * @param validationMessages
	 * @return array com mensagens traduzidas
	 */
	public String[]  availableInvariantMessages(Set<ConstraintViolation<Object>> validationMessages)  {

		if (validationMessages!=null) {
			String[] mensagens = new String[validationMessages.size()];

			String msg=null;
			String msgCampo=null;

			Iterator<ConstraintViolation<Object>> iterator = validationMessages.iterator();
			int i=0;
			while(iterator.hasNext()) {
				ConstraintViolation<Object> cv = iterator.next();

				msg = cv.getMessage();

				int posIni = msg.indexOf("{");
				if (posIni>-1) {
					int posFim = msg.indexOf("}");
					String token = msg.substring(posIni,posFim+1);
					msgCampo = cv.getPropertyPath().toString();
					msg = StringUtils.replaceOnce(msg,token,msgCampo);
				}
				mensagens[i++] = msg;

			}
			return mensagens;
		}
		return null;

	}
	
	/**
	 * Interceptor para validadar a entidade da requisição.
	 * 
	 * Trata a exceção e dispara uma mensagem para o Back-End.
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
		
		Annotation[][] annotations = context.getMethod().getParameterAnnotations();
		
		for(int i=0; i < annotations.length; i++ ){
			for(Annotation annotation: annotations[i]) {
				if(annotation.annotationType().equals(Valid.class)){
					checkBeanValidation(context.getParameters()[i]);
				}
			}
		}
		
		Object result = context.proceed();
		return result;
	}

	/**
	 * Método para a validação invariante do formulário na gravação simples.
	 * 
	 * @param entityPlc
	 * @param groups
	 */
	public void checkBeanValidation(Object entityPlc, Class<?>... groups)  {

	 	// Validação invariante padrão Hibernate Validator.
		Set<ConstraintViolation<Object>> cv = null;
		
		if (entityPlc!=null) {
			try {
				cv = validationUtil.invariantValidation(entityPlc, groups);
			}catch(PlcException ePlc){
				throw ePlc;
			} catch (Exception e) {
				e.printStackTrace();
				throw PlcBeanMessages.FALHA_OPERACAO_003.create();
			}	
		}	
		boolean validacaoInvarianteOk = (cv == null || cv.isEmpty());

		//invocando a validação
		if (!validacaoInvarianteOk) {
			throw PlcBeanMessages.FALHA_VALIDACAO_023.create(availableInvariantMessages(cv));
		}
		
	}
}
