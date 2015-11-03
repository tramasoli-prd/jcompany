package br.jus.tjrs.cronos.app.rest.ctj;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.UsuarioCTJDTO;
import br.jus.tjrs.cronos.app.model.service.UsuarioCTJService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.AbstractRest;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;

@Path("/usuarioCTJ")
@Authenticated
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UsuarioCTJRest extends AbstractRest
{

   @Inject
   private UsuarioCTJService usuarioCTJService;

   @GET
   @Path("/listaPessoas/{nome}")
   public List<UsuarioCTJDTO> listaPessoas(@PathParam("nome") String nome) throws CronosException
   {
      return usuarioCTJService.listaPessoas(nome);
   }
}
