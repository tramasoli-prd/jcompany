package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.entity.TokenEntity;
import br.jus.tjrs.cronos.app.model.service.TokenService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/token")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class TokenRest extends AbstractEntityRest<Long, TokenEntity, Object>
{
   @Inject
   private TokenService tokenService;

   @GET
   @Path("/all")
   public List<TokenEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected TokenService getEntityService()
   {
      return tokenService;
   }
}
