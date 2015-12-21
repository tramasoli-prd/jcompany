/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.util.message;

import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Utilitário para manipulação dinâmico dos conteúdos das mensagens.
 * 
 * @category Util
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@ApplicationScoped
public class PlcMessageUtil {

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

}
