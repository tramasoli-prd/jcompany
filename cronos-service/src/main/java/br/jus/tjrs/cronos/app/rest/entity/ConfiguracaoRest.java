package br.jus.tjrs.cronos.app.rest.entity;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.ConfiguracaoDTO;
import br.jus.tjrs.cronos.app.model.entity.ConfiguracaoEntity;
import br.jus.tjrs.cronos.app.model.service.ConfiguracaoService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/configuracao")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ConfiguracaoRest extends AbstractEntityRest<Long, ConfiguracaoEntity, Object>
{
   @Inject
   private ConfiguracaoService configuracaoService;

   @Override
   protected ConfiguracaoService getEntityService()
   {
      return configuracaoService;
   }

   @POST
   @Path("/permitirEditarMenu")
   public ConfiguracaoEntity permitirEditarMenu(ConfiguracaoDTO configurar) throws CronosException
   {
      return getEntityService().permitirEditarMenu(configurar);
   }

   @GET
   @Path("/findConfiguracaoByGrupoTrabalho/{idGrupoTrabalho}")
   public ConfiguracaoEntity findConfiguracaoByGrupoTrabalho(@PathParam("idGrupoTrabalho") Long idGrupoTrabalho)
            throws CronosException
   {
      return getEntityService().findConfiguracaoByGrupoTrabalho(idGrupoTrabalho);
   }

}
