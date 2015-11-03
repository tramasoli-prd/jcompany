package br.jus.tjrs.cronos.app.rest.entity;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.SentencaDTO;
import br.jus.tjrs.cronos.app.model.entity.SentencaEntity;
import br.jus.tjrs.cronos.app.model.service.SentencaService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/sentenca")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class SentencaRest extends AbstractEntityRest<Long, SentencaEntity, Object>
{
   @Inject
   private SentencaService sentencaService;

   @Override
   protected SentencaService getEntityService()
   {
      return sentencaService;
   }

   @POST
   @Path("/carregarSentenca")
   public SentencaDTO carregarSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      return getEntityService().carregarSentenca(sentencaDTO);
   }

   @POST
   @Path("/carregarSentencaId")
   public SentencaDTO carregarSentencaId(SentencaDTO sentencaDTO) throws CronosException
   {

      return getEntityService().carregarSentencaId(sentencaDTO);
   }

   @POST
   @Path("/salvarSentenca")
   public SentencaDTO salvarSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      return getEntityService().salvarSentenca(sentencaDTO);
   }

   @POST
   @Path("/finalizarSentenca")
   public SentencaDTO finalizarSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      return getEntityService().finalizarSentenca(sentencaDTO);
   }

   @POST
   @Path("/removerSentenca")
   public boolean removerSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      return getEntityService().removerSentenca(sentencaDTO);
   }

   @POST
   @Path("/findByStatusFinalizada")
   public SentencaEntity findByStatusFinalizada(SentencaDTO sentencaDTO) throws CronosException
   {
      return getEntityService().findByStatusFinalizada(sentencaDTO);
   }

}