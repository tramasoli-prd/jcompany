package br.jus.tjrs.cronos.app.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ApplicationRest
{
   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String get()
   {
      return "cronos-service";
   }
}
