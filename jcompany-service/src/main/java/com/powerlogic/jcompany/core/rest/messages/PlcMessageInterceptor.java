package com.powerlogic.jcompany.core.rest.messages;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import com.powerlogic.jcompany.commons.util.message.PlcMsgUtil;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;

@Provider
@PlcMessageIntercept
public class PlcMessageInterceptor implements WriterInterceptor
{


	@Inject
	private PlcMsgUtil msgUtil;

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException
	{
		Object entity = context.getEntity();
		if (!entity.getClass().isAssignableFrom(PlcMessageMap.class)){ // não deu exceção
			if (entity.getClass().getCanonicalName()!=null && !entity.getClass().getCanonicalName().equals("byte[]")) {
				PlcResponseEntity responseEntity = new PlcResponseEntity();
				responseEntity.setEntity(entity);
				responseEntity.setMessageMap(msgUtil.getMensagens());
	
				context.setGenericType(PlcResponseEntity.class);
				context.setEntity(responseEntity);
			}
		}
		context.proceed();
	}

}
