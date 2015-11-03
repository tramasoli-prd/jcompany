package br.jus.tjrs.cronos.core.rest.exception;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.messages.MessageMap;
import br.jus.tjrs.cronos.core.messages.MessageTranslator;

@Provider
public class CronosExceptionMapper implements ExceptionMapper<CronosException>
{
   @Inject
   private MessageTranslator messageTranslate;

   private boolean shouldTranslate = false;

   @Override
   public Response toResponse(CronosException cronosException)
   {
      MessageMap messageMap = cronosException.getMessageMap();
      if (shouldTranslate)
      {
         messageMap = translateMessageMap(messageMap);
      }

      return Response
               .serverError()
               .type(MediaType.APPLICATION_JSON)
               .entity(messageMap)
               .build();
   }

   protected MessageMap translateMessageMap(MessageMap messageMap)
   {
      return messageTranslate.translate(messageMap);
   }
}
