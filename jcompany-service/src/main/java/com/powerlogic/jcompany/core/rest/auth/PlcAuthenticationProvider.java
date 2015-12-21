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
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;

import com.powerlogic.jcompany.core.exception.PlcException;

@ApplicationScoped
public class PlcAuthenticationProvider {
	

	public PlcAuthenticatedUserInfo createUser(final Principal userPrincipal, final String host) throws PlcException {
		PlcAuthenticatedUserInfo user = new PlcAuthenticatedUserInfo(userPrincipal.getName(), host, new ArrayList<String>());
		return user;
	}
}
