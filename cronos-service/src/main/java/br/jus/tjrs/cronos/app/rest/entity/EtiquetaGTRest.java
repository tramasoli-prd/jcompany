package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.IconeDTO;
import br.jus.tjrs.cronos.app.model.dto.ListPartEtiquetaDTO;
import br.jus.tjrs.cronos.app.model.entity.EtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.service.EtiquetaGTService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/etiquetaGT")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class EtiquetaGTRest extends AbstractEntityRest<Long, EtiquetaGTEntity, Object>
{
   @Inject
   private EtiquetaGTService etiquetaGTService;

   @GET
   @Path("/all")
   public List<EtiquetaGTEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected EtiquetaGTService getEntityService()
   {
      return etiquetaGTService;
   }

   @POST
   @Path("/listByPartDescription")
   public List<EtiquetaGTEntity> listByPartDescription(ListPartEtiquetaDTO list)
            throws CronosException
   {
      return getEntityService().listByPartDescription(list.getPart(), list.getIdGrupoTrabalho());
   }

   @POST
   @Path("/alterarIcone")
   public EtiquetaGTEntity alterarIcone(IconeDTO icone) throws CronosException
   {
      return getEntityService().alterarIcone(icone);
   }

   @POST
   @Path("/salvarEtiqueta")
   public EtiquetaGTEntity salvarEtiqueta(IconeDTO icone) throws CronosException
   {
      return getEntityService().alterarIcone(icone);
   }

}
