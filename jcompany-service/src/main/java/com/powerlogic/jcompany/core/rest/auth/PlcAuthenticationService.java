/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.

		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
 */

package com.powerlogic.jcompany.core.rest.auth;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;

public class PlcAuthenticationService  {
	
	@Inject
	private PlcAuthenticationProvider authProvider;
	
	@Inject
	private PlcProfileProvider profileProvider;

	public PlcAuthenticatedUserInfo user(@Context HttpServletRequest request) throws PlcException {
		return getUserInfo(request);
	}


	public PlcAuthenticatedUserInfo login(@Context HttpServletRequest request, Map<String, String> data)
			throws PlcException {
		String username = data.get("username");
		String password = data.get("password");

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		// authenticate!
		try {
			if (request.getUserPrincipal()==null) {
				request.login(username, password);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			userLogout(request);
			if (e.getCause() instanceof FailedLoginException) {
				throw PlcBeanMessages.FALHA_LOGIN_001.create();
			}
			throw PlcBeanMessages.FALHA_OPERACAO_003.create();
		}
		// create user info!
		return userLogin(request);
	}
	
	public boolean logout(@Context HttpServletRequest request) {
		PlcAuthenticatedUserInfo userInfo = userLogout(request);

		if (userInfo != null) {
			return true;
		}
		return false;
	}

	public PlcAuthenticatedUserInfo checkSession(@Context HttpServletRequest request) throws PlcException {
		if (request != null) {
			HttpSession session = request.getSession(false);

			if (session != null) {
				return (PlcAuthenticatedUserInfo) session.getAttribute(PlcAuthenticatedUserInfo.PROPERTY);
			}
		}
		return null;
	}
	
	protected PlcAuthenticatedUserInfo getUserInfo(HttpServletRequest request) throws PlcException {
		HttpSession session = request.getSession(false);

		if (session != null) {
			PlcAuthenticatedUserInfo user = (PlcAuthenticatedUserInfo) session.getAttribute(PlcAuthenticatedUserInfo.PROPERTY);
			return user;
		}
		return null;
	}


	protected PlcAuthenticatedUserInfo userLogin(HttpServletRequest request) throws PlcException {
		try {
			Principal userPrincipal = request.getUserPrincipal();
			PlcAuthenticatedUserInfo userInfo;
			userInfo = authProvider.createUser(userPrincipal, request.getRemoteHost());
			request.getSession().setAttribute(PlcAuthenticatedUserInfo.PROPERTY, userInfo);
			userInfo = profileProvider.createProfile(request);
			return userInfo;
		} catch (PlcException e) {
			userLogout(request);
			throw e;
		} catch (Exception e) {
			userLogout(request);
			throw PlcBeanMessages.FALHA_OPERACAO_003.create();
		}
	}

	protected PlcAuthenticatedUserInfo userLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			PlcAuthenticatedUserInfo userInfo = (PlcAuthenticatedUserInfo) session.getAttribute(PlcAuthenticatedUserInfo.PROPERTY);
			if (userInfo != null) {
				session.removeAttribute(PlcAuthenticatedUserInfo.PROPERTY);
			}
			session.invalidate();
			try {
				request.logout();
			} catch (ServletException e) {
				throw PlcBeanMessages.FALHA_OPERACAO_003.create();
			}
			return userInfo;
		}
		return null;
	}
}
