/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.

		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
 */

package com.powerlogic.jcompany.core.rest.auth;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.rest.PlcAbstractRest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/auth")
@PlcAuthenticated
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class PlcAuthenticationRest extends PlcAbstractRest {
	
	@Inject
	private PlcAuthenticationService authService;

	@POST
	@Path("/login")
	@PlcNotAuthenticated
	public PlcAuthenticatedUserInfo login(@Context HttpServletRequest request, Map<String, String> data) throws PlcException {
		return authService.login(request, data);
	}

	@POST
	@Path("/logout")
	@PlcNotAuthenticated
	public boolean logout(@Context HttpServletRequest request) {
		return authService.logout(request);
	}

	@GET
	@Path("/checkSession")
	@PlcNotAuthenticated
	public PlcAuthenticatedUserInfo checkSession(@Context HttpServletRequest request) throws PlcException {
		return authService.checkSession(request);
	}

}
