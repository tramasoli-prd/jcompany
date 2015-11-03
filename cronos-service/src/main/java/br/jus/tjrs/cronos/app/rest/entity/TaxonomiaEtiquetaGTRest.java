package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.service.TaxonomiaEtiquetaGTService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/taxonomiaEtiquetaGT")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class TaxonomiaEtiquetaGTRest extends AbstractEntityRest<Long, TaxonomiaEtiquetaGTEntity, Object>
{
   @Inject
   private TaxonomiaEtiquetaGTService taxonomiaEtiquetaGTService;

   @GET
   @Path("/all")
   public List<TaxonomiaEtiquetaGTEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   protected TaxonomiaEtiquetaGTService getEntityService()
   {
      return taxonomiaEtiquetaGTService;
   }
}
