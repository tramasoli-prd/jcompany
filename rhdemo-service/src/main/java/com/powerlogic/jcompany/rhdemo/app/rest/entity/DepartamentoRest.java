package com.powerlogic.jcompany.rhdemo.app.rest.entity;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticated;
import com.powerlogic.jcompany.core.rest.entity.PlcAbstractEntityRest;
import com.powerlogic.jcompany.core.rest.messages.PlcMessageIntercept;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.service.IDepartamentoService;

@PlcAuthenticated
@Path("/entity/departamento")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@PlcMessageIntercept
public class DepartamentoRest extends PlcAbstractEntityRest<Long, DepartamentoEntity, Object> {
	
   @Inject
   private IDepartamentoService departamentoService;

   @Override
   protected IDepartamentoService getEntityService() {
      return departamentoService;
   }

}