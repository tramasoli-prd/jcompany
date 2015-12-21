/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest.exception;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import com.powerlogic.jcompany.core.messages.PlcMessageEntry;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;

/**
 * 
 * Classe no padrão Interceptor.
 * 
 * Responsável por apoiar no tratamento de Exceção do Jaguar, mantendo a exceção "raiz" para tratamento genérico no View.
 * 
 * Descobrindo o tipo de objeto no response, é possível manipular as mensagens de maneira dinâmica.
 * 
 * @category Interceptor
 * @author Powerlogic
 * @since Jaguar 1.0.0
 */
@Provider
public class PlcExceptionInterceptor implements WriterInterceptor {
	
   @Override
   public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
	   
      Object entity = context.getEntity();
      
      if (entity instanceof PlcMessageMap || entity instanceof PlcMessageEntry) {
         context.setGenericType(null);
      }
      
      context.proceed();
   }
}