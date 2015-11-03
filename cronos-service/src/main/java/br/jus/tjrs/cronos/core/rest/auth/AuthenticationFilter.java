package br.jus.tjrs.cronos.core.rest.auth;

import java.io.IOException;
import java.security.Principal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AuthenticationFilter implements ContainerRequestFilter
{
   private Authenticated authenticated;

   public AuthenticationFilter(Authenticated authenticated)
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

   public Authenticated getAuthenticated()
   {
      return authenticated;
   }
}