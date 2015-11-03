package br.jus.tjrs.cronos.app.ws;

import javax.inject.Inject;
import javax.jws.WebService;

import br.jus.tjrs.cronos.app.model.service.ComarcaService;
import br.jus.tjrs.cronos.app.model.service.SentencaService;
import br.jus.tjrs.cronos.core.CronosException;

@WebService(endpointInterface = "br.jus.tjrs.cronos.app.ws.CronosWS")
public class CronosWSImpl implements CronosWS
{

   @Inject
   SentencaService sentencaService;

   @Inject
   ComarcaService comarcaService;

   @Override
   public String exportarSentenca(String cnj) throws CronosException
   {
      return sentencaService.exportarSentenca(cnj);
   }

   @Override
   public String buscaComarca(Integer idComarca) throws CronosException
   {
      return comarcaService.buscaComarca(idComarca);
   }
}
