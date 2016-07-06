package com.powerlogic.jcompany.rhdemo.auth;

import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticationProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

@ApplicationScoped
@Specializes
public class AuthenticationProvider extends PlcAuthenticationProvider
{
	@Inject
	private WeblogicIntegration weblogicIntegration;

	/*@Override
	public PlcAuthenticatedUserInfo createUser(final Principal userPrincipal, final String host, final List<String> roles)
	{
		PlcAuthenticatedUserInfo user = new PlcAuthenticatedUserInfo(userPrincipal.getName(), host, weblogicIntegration.getRoles());
		weblogicIntegration.loadExtraInfo(user, userPrincipal);
		return user;
	}*/


}
