package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoLoginDTO;
import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoNodoDTO;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.model.service.GrupoTrabalhoService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/grupoTrabalho")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class GrupoTrabalhoRest extends AbstractEntityRest<Long, GrupoTrabalhoEntity, Object>
{
   @Inject
   private GrupoTrabalhoService grupoTrabalhoService;

   @Override
   protected GrupoTrabalhoService getEntityService()
   {
      return grupoTrabalhoService;
   }

   @GET
   @Path("/all")
   public List<GrupoTrabalhoEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @GET
   @Path("/findTreeView/{idGrupoTrabalho}")
   public GrupoTrabalhoNodoDTO listTree(@PathParam("idGrupoTrabalho") Long idGrupoTrabalho) throws CronosException
   {
      return getEntityService().findTreeViewDTO(idGrupoTrabalho);
   }

   @GET
   @Path("/findByUsuario/{idUsuario}")
   public List<GrupoTrabalhoLoginDTO> findByUsuario(@PathParam("idUsuario") Long idUsuario) throws CronosException
   {
      return getEntityService().findByUsuario(idUsuario);
   }

   @POST
   @Path("/alteraPadrao")
   public boolean alteraPadrao(GrupoTrabalhoLoginDTO grupo) throws CronosException
   {
      return getEntityService().alteraPadrao(grupo);
   }

}