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

import javax.enterprise.context.ApplicationScoped;
import java.security.Principal;
import java.util.List;

@ApplicationScoped
public class PlcAuthenticationProvider {

	/**
	 *
	 * @param userPrincipal
	 * @param host
	 * @return
	 * @throws PlcException
     */
	public PlcAuthenticatedUserInfo createUser(final Principal userPrincipal, final String host) throws PlcException {
		PlcAuthenticatedUserInfo user = new PlcAuthenticatedUserInfo(userPrincipal.getName(), host);
		return user;
	}

	/**
	 *
	 * @param userPrincipal
	 * @param host
	 * @param roles
	 * @return
	 * @throws PlcException
     */
	public PlcAuthenticatedUserInfo createUser(final Principal userPrincipal, final String host, final List<String> roles) throws PlcException {
		PlcAuthenticatedUserInfo user = new PlcAuthenticatedUserInfo(userPrincipal.getName(), host, roles);
		return user;
	}
}
