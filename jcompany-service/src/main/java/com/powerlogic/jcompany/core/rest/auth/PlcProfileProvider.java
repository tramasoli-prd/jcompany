package com.powerlogic.jcompany.core.rest.auth;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import com.powerlogic.jcompany.core.exception.PlcException;

@Stateless
public class PlcProfileProvider {
	
	public PlcAuthenticatedUserInfo createProfile(HttpServletRequest request) throws PlcException {
		return (PlcAuthenticatedUserInfo)request.getSession().getAttribute(PlcAuthenticatedUserInfo.PROPERTY);
	}

}
