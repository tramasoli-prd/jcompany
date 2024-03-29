/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest.auth;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class PlcAuthenticationFeature implements DynamicFeature
{
   private static final Logger LOGGER = LoggerFactory.getLogger(PlcAuthenticationFeature.class);

   @Context
   private HttpServletRequest requestProxy;

   
   @Override
   public void configure(ResourceInfo resourceInfo, FeatureContext context)
   {
      PlcAuthenticated authenticated = getAuthenticated(resourceInfo);

      if (authenticated != null)
      {
         LOGGER.debug("Authenticated REST Resource {}", resourceInfo.getResourceMethod());

         context.register(createFilter(authenticated));
      }
   }

   protected PlcAuthenticationFilter createFilter(PlcAuthenticated authenticated)
   {
      return new PlcAuthenticationFilter(authenticated, requestProxy);
   }

   private PlcAuthenticated getAuthenticated(ResourceInfo resourceInfo)
   {
      // method override class authentication
      if (resourceInfo.getResourceMethod().isAnnotationPresent(PlcNotAuthenticated.class))
      {
         return null;
      }
      // method override class authentication
      PlcAuthenticated authenticated = resourceInfo.getResourceMethod().getAnnotation(PlcAuthenticated.class);
      if (authenticated == null)
      {
         // class indicate default authentication
         authenticated = resourceInfo.getResourceClass().getAnnotation(PlcAuthenticated.class);
      }
      return authenticated;
   }
}
