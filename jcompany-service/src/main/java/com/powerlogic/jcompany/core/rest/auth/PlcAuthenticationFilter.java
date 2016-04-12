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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PlcAuthenticationFilter implements ContainerRequestFilter
{
	private PlcAuthenticated authenticated;

	private HttpServletRequest requestProxy;

	public PlcAuthenticationFilter(PlcAuthenticated authenticated, HttpServletRequest requestProxy)
	{
		this.authenticated = authenticated;
		this.requestProxy = requestProxy;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		PlcAuthenticatedUserInfo userInfo = null;
		try {
			if (requestProxy!=null && requestProxy.getSession()!=null) {
				userInfo = (PlcAuthenticatedUserInfo)requestProxy.getSession().getAttribute(PlcAuthenticatedUserInfo.PROPERTY);
			} 
		} catch(Exception e){}

		// testar UserPrincipal ou userInfo 
		if (requestContext.getSecurityContext().getUserPrincipal()==null && userInfo == null)
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