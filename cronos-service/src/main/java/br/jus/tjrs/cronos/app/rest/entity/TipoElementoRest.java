package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.entity.TipoElementoEntity;
import br.jus.tjrs.cronos.app.model.service.TipoElementoService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/tipoElemento")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class TipoElementoRest extends AbstractEntityRest<Long, TipoElementoEntity, Object>
{
   @Inject
   private TipoElementoService tipoElementoService;

   @GET
   @Path("/all")
   public List<TipoElementoEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected TipoElementoService getEntityService()
   {
      return tipoElementoService;
   }
}
