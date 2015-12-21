/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest.exception;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.messages.PlcMessageTranslator;

/**
 * 
 * Classe no padrão Provider.
 * 
 * A manipulação e tradução das mensagens lançadas a partir das "Exceções Controladas" fornecido pelo Jaguar.
 * 
 * A internacionalização (I18N) dos erros com base nos arquivos de mensagens (*.properties).
 * 
 * Agrupa todas as Exceções em um Mapa, organizando as mensagens de erros que serão disparadas no retorno da requisição.
 * 
 * @category Provider
 * @author Powerlogic
 * @since Jaguar 1.0.0
 */
@Provider
public class PlcExceptionMapper implements ExceptionMapper<PlcException> {
	
	private boolean shouldTranslate = false;

	@Inject
	private PlcMessageTranslator messageTranslate;

	@Override
	public Response toResponse(PlcException exception) {
		
		PlcMessageMap messageMap = exception.getMessageMap();

		//TODO: Controlar a propriedade para que seja possível traduzir a exception
		if (shouldTranslate) {
			
			messageMap = translateMessageMap(messageMap);
		}

		return Response.serverError().type(MediaType.APPLICATION_JSON).entity(messageMap).build();
	}

	protected PlcMessageMap translateMessageMap(PlcMessageMap messageMap) {
		return messageTranslate.translate(messageMap);
	}
}
