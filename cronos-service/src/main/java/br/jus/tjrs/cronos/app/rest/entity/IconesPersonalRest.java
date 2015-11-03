package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.CssDTO;
import br.jus.tjrs.cronos.app.model.entity.IconesPersonalEntity;
import br.jus.tjrs.cronos.app.model.service.IconesPersonalService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/iconesPersonal")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class IconesPersonalRest extends AbstractEntityRest<Long, IconesPersonalEntity, Object>
{
   @Inject
   private IconesPersonalService iconesPersonalService;

   @GET
   @Path("/all")
   public List<IconesPersonalEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected IconesPersonalService getEntityService()
   {
      return iconesPersonalService;
   }

   @GET
   @Path("/css")
   public CssDTO css() throws CronosException
   {
      return getEntityService().css();
   }
}
