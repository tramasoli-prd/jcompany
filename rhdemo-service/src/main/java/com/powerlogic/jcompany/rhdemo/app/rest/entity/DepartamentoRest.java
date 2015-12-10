package com.powerlogic.jcompany.rhdemo.app.rest.entity;

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
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.service.DepartamentoService;

@PlcAuthenticated
@Path("/entity/departamento")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@PlcMessageIntercept
public class DepartamentoRest extends PlcAbstractEntityRest<Long, DepartamentoEntity, Object> {
	
   @Inject
   private DepartamentoService departamentoService;

   @GET
   @Path("/findByRoot")
   public DepartamentoEntity findByRoot(@BeanParam PlcSearchBuilder searchBuilder, @QueryParam("root") Long id) throws PlcException {
      return getEntityService().findByRoot(id);
   }
   

   @Override
   protected DepartamentoService getEntityService() {
      return departamentoService;
   }

}