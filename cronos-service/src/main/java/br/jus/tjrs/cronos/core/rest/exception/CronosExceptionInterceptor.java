package br.jus.tjrs.cronos.core.rest.exception;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import br.jus.tjrs.cronos.core.messages.MessageEntry;
import br.jus.tjrs.cronos.core.messages.MessageMap;

@Provider
public class CronosExceptionInterceptor implements WriterInterceptor
{
   @Override
   public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException
   {
      Object entity = context.getEntity();
      if (entity instanceof MessageMap || entity instanceof MessageEntry)
      {
         context.setGenericType(null);
      }
      context.proceed();
   }
}
