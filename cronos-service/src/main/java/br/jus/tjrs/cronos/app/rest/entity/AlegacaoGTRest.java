package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.entity.AlegacaoGTEntity;
import br.jus.tjrs.cronos.app.model.service.AlegacaoGTService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/alegacaoGT")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class AlegacaoGTRest extends AbstractEntityRest<Long, AlegacaoGTEntity, Object>
{
   @Inject
   private AlegacaoGTService alegacaoGTService;

   @GET
   @Path("/all")
   public List<AlegacaoGTEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected AlegacaoGTService getEntityService()
   {
      return alegacaoGTService;
   }

   @GET
   @Path("/findAllByGrupoTrabalho/{idGrupoTrabalho}")
   public List<AlegacaoGTEntity> findAllByGrupoTrabalho(@PathParam("idGrupoTrabalho") Long idGrupoTrabalho)
            throws CronosException
   {
      return getEntityService().findAllByGrupoTrabalho(idGrupoTrabalho);
   }

}
