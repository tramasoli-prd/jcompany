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

import br.jus.tjrs.cronos.app.model.dto.AutoCompleteDTO;
import br.jus.tjrs.cronos.app.model.dto.CarregarCategoriaDTO;
import br.jus.tjrs.cronos.app.model.dto.CategoriaItemDTO;
import br.jus.tjrs.cronos.app.model.dto.CloneCategoriaItemDTO;
import br.jus.tjrs.cronos.app.model.dto.IconeDTO;
import br.jus.tjrs.cronos.app.model.dto.PesquisaGeralDTO;
import br.jus.tjrs.cronos.app.model.dto.RetornoPesquisaGeralDTO;
import br.jus.tjrs.cronos.app.model.dto.SaveNodeDTO;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.model.service.CategoriaItemService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/categoriaItem")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class CategoriaItemRest extends AbstractEntityRest<Long, CategoriaItemEntity, Object>
{
   @Inject
   private CategoriaItemService categoriaItemService;

   @Override
   protected CategoriaItemService getEntityService()
   {

      return categoriaItemService;
   }

   @POST
   @Path("/carregarCategoria")
   public CategoriaItemDTO carregarCategoria(CarregarCategoriaDTO carregarCategoria)
            throws CronosException
   {
      return getEntityService().carregarCategoria(carregarCategoria);
   }

   @POST
   @Path("/salvarTopico")
   public CategoriaItemEntity salvarTopico(CategoriaItemDTO topicos) throws CronosException
   {
      return getEntityService().salvarTopico(topicos);
   }

   @POST
   @Path("/listByPartDescription")
   public List<CategoriaItemEntity> listByPartDescription(AutoCompleteDTO autocomplete)
            throws CronosException
   {
      return getEntityService().listByPartDescription(autocomplete);
   }

   @POST
   @Path("/listNodeByPartDescription")
   public List<CategoriaItemEntity> listNodeByPartDescription(AutoCompleteDTO autocomplete)
            throws CronosException
   {
      return getEntityService().listNodeByPartDescription(autocomplete);
   }

   @POST
   @Path("/alterarIcone")
   public CategoriaItemEntity alterarIcone(IconeDTO icone) throws CronosException
   {
      return getEntityService().alterarIcone(icone);
   }

   @POST
   @Path("/buscarPesquisaGeral")
   public List<RetornoPesquisaGeralDTO> buscarPesquisaGeral(PesquisaGeralDTO part)
   {
      try
      {
         return getEntityService().buscarPesquisaGeral(part);
      }
      catch (CronosException e)
      {
         return null;
      }
   }

   @POST
   @Path("/cloneItem")
   public CategoriaItemEntity cloneItem(CloneCategoriaItemDTO categoria) throws CronosException
   {
      return getEntityService().cloneItem(categoria);
   }

   @POST
   @Path("/saveNode")
   public CategoriaItemEntity saveNode(SaveNodeDTO node) throws CronosException
   {
      return getEntityService().saveNode(node);
   }

   @GET
   @Path("/listTopicosByIdModelo/{id}")
   public List<CategoriaItemEntity> listTopicosByIdModelo(@PathParam("id") Long id)
            throws CronosException
   {
      return getEntityService().listTopicosByIdModelo(id);
   }
}
