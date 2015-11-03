package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.model.service.ElementoIGTService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/elementoIGT")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ElementoIGTRest extends AbstractEntityRest<Long, ElementoIGTEntity, Object>
{
   @Inject
   private ElementoIGTService elementoIGTService;

   @GET
   @Path("/all")
   public List<ElementoIGTEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected ElementoIGTService getEntityService()
   {
      return elementoIGTService;
   }

   @GET
   @Path("/listByPartDescription/{part}")
   public List<ElementoIGTEntity> listByPartDescription(@PathParam("part") String part)
            throws CronosException
   {
      return getEntityService().listByPartDescription(part);
   }
}
