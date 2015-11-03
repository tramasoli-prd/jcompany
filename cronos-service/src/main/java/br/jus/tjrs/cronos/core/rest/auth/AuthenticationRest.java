package br.jus.tjrs.cronos.core.rest.auth;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.AbstractRest;

@Path("/auth")
@Authenticated
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class AuthenticationRest extends AbstractRest
{
   @Inject
   private AuthenticationProvider authProvider;

   @GET
   @Path("/user")
   public AuthenticatedUserInfo user(@Context HttpServletRequest request) throws CronosException
   {
      return getUserInfo(request);
   }

   @GET
   @Path("/changeSession")
   public AuthenticatedUserInfo changeSession(@Context HttpServletRequest request) throws CronosException
   {
      return userLogin(request);
   }

   @GET
   @Path("/check")
   @Deprecated
   public AuthenticatedUserInfo check(@Context HttpServletRequest request) throws CronosException
   {
      return user(request);
   }

   @POST
   @Path("/login")
   @NotAuthenticated
   public AuthenticatedUserInfo login(@Context HttpServletRequest request, Map<String, String> data)
            throws CronosException
   {
      String username = data.get("username");
      String password = data.get("password");

      if (username == null || username.isEmpty() || password == null || password.isEmpty())
      {
         throw new WebApplicationException(Status.BAD_REQUEST);
      }
      // authenticate!
      try
      {
         request.login(username, password);
      }
      catch (Exception e)
      {
         // e.printStackTrace();
         userLogout(request);
         if (e.getCause() instanceof FailedLoginException)
         {
            throw CronosErrors.FALHA_LOGIN_001.create();
         }
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
      // create user info!
      return userLogin(request);
   }

   @POST
   @Path("/logout")
   @NotAuthenticated
   public boolean logout(@Context HttpServletRequest request)
   {
      AuthenticatedUserInfo userInfo = userLogout(request);

      if (userInfo != null)
      {
         return true;
      }
      return false;
   }

   protected AuthenticatedUserInfo getUserInfo(HttpServletRequest request) throws CronosException
   {
      HttpSession session = request.getSession(false);

      if (session != null)
      {
         AuthenticatedUserInfo user = (AuthenticatedUserInfo) session.getAttribute(AuthenticatedUserInfo.PROPERTY);
         if (user != null)
         {
            user = authProvider.changeUser(user);
            request.getSession().setAttribute(AuthenticatedUserInfo.PROPERTY, user);
         }
         return user;
      }
      return null;
   }

   @GET
   @Path("/checkSession")
   @NotAuthenticated
   public AuthenticatedUserInfo checkSession(@Context HttpServletRequest request) throws CronosException
   {
      if (request != null)
      {
         HttpSession session = request.getSession(false);

         if (session != null)
         {
            return (AuthenticatedUserInfo) session.getAttribute(AuthenticatedUserInfo.PROPERTY);
         }
      }
      return null;
   }

   protected AuthenticatedUserInfo userLogin(HttpServletRequest request) throws CronosException
   {
      try
      {
         Principal userPrincipal = request.getUserPrincipal();
         AuthenticatedUserInfo userInfo;
         userInfo = authProvider.createUser(userPrincipal, request.getRemoteHost());
         request.getSession().setAttribute(AuthenticatedUserInfo.PROPERTY, userInfo);
         return userInfo;
      }
      catch (CronosException e)
      {
         userLogout(request);
         throw e;
      }
      catch (Exception e)
      {
         userLogout(request);
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }

   protected AuthenticatedUserInfo userLogout(HttpServletRequest request)
   {
      HttpSession session = request.getSession(false);

      if (session != null)
      {
         AuthenticatedUserInfo userInfo = (AuthenticatedUserInfo) session.getAttribute(AuthenticatedUserInfo.PROPERTY);
         if (userInfo != null)
         {
            session.removeAttribute(AuthenticatedUserInfo.PROPERTY);
         }
         session.invalidate();
         return userInfo;
      }
      return null;
   }
}
