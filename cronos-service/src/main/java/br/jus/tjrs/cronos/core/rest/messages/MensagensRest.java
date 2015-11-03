package br.jus.tjrs.cronos.core.rest.messages;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.core.messages.MessageMap;
import br.jus.tjrs.cronos.core.messages.MessageTranslator;
import br.jus.tjrs.cronos.core.rest.auth.NotAuthenticated;

@NotAuthenticated
@Path("/mensagens")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class MensagensRest
{
   @Inject
   private MessageTranslator messageTranslator;

   @GET
   public MessageMap getMensagens()
   {
      return messageTranslator.getAllMessages();
   }
}
