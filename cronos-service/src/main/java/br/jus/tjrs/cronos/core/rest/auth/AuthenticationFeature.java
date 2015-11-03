package br.jus.tjrs.cronos.core.rest.auth;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class AuthenticationFeature implements DynamicFeature
{
   private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFeature.class);

   @Override
   public void configure(ResourceInfo resourceInfo, FeatureContext context)
   {
      Authenticated authenticated = getAuthenticated(resourceInfo);

      if (authenticated != null)
      {
         LOGGER.debug("Authenticated REST Resource {}", resourceInfo.getResourceMethod());

         context.register(createFilter(authenticated));
      }
   }

   protected AuthenticationFilter createFilter(Authenticated authenticated)
   {
      return new AuthenticationFilter(authenticated);
   }

   private Authenticated getAuthenticated(ResourceInfo resourceInfo)
   {
      // method override class authentication
      if (resourceInfo.getResourceMethod().isAnnotationPresent(NotAuthenticated.class))
      {
         return null;
      }
      // method override class authentication
      Authenticated authenticated = resourceInfo.getResourceMethod().getAnnotation(Authenticated.class);
      if (authenticated == null)
      {
         // class indicate default authentication
         authenticated = resourceInfo.getResourceClass().getAnnotation(Authenticated.class);
      }
      return authenticated;
   }
}
