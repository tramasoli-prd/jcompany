package com.powerlogic.jcompany.rhdemo.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.rest.PlcSearchBuilder;
import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticated;
import com.powerlogic.jcompany.core.rest.entity.PlcAbstractEntityRest;
import com.powerlogic.jcompany.core.rest.messages.PlcMessageIntercept;
import com.powerlogic.jcompany.rhdemo.app.model.entity.UnidadeFederativaEntity;
import com.powerlogic.jcompany.rhdemo.app.model.service.UnidadeFederativaService;

@PlcAuthenticated
@Path("/entity/unidadefederativa")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@PlcMessageIntercept
public class UnidadeFederativaRest extends PlcAbstractEntityRest<Long, UnidadeFederativaEntity, Object> {

	@Inject
	private UnidadeFederativaService unidadeFederativaService;
	

	@GET
	@Path("/findByNome")
	public UnidadeFederativaEntity find(@BeanParam PlcSearchBuilder searchBuilder, @QueryParam("nome") String nome) throws PlcException	{
		return getEntityService().findByNome(nome);
	}


	@Override
	protected UnidadeFederativaService getEntityService()	{
		return unidadeFederativaService;
	}

}
