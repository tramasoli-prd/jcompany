/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest.auth;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PlcAuthenticationFilter implements ContainerRequestFilter
{
   private PlcAuthenticated authenticated;

   public PlcAuthenticationFilter(PlcAuthenticated authenticated)
   {
      this.authenticated = authenticated;
   }

   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException
   {
      Principal userPrincipal = requestContext.getSecurityContext().getUserPrincipal();

      if (userPrincipal == null)
      {
         // not authenticated
         requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
      }
   }

   public PlcAuthenticated getAuthenticated()
   {
      return authenticated;
   }
}