package br.jus.tjrs.cronos.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.AceiteTermoDTO;
import br.jus.tjrs.cronos.app.model.dto.UsuarioDTO;
import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity;
import br.jus.tjrs.cronos.app.model.service.UsuarioService;
import br.jus.tjrs.cronos.app.rest.SearchBuilder;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.cronos.core.rest.entity.AbstractEntityRest;

@Authenticated
@Path("/entity/usuario")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UsuarioRest extends AbstractEntityRest<Long, UsuarioEntity, Object>
{
   @Inject
   private UsuarioService usuarioService;

   @GET
   @Path("/all")
   public List<UsuarioEntity> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @GET
   @Path("/findByCpf")
   public UsuarioEntity find(@BeanParam SearchBuilder searchBuilder, @QueryParam("cpf") String cpf)
            throws CronosException
   {
      return getEntityService().findByCpf(cpf);
   }

   @POST
   @Path("/salvarUsuario")
   public UsuarioDTO salvarUsuario(UsuarioDTO usuario) throws CronosException
   {
      return getEntityService().salvarUsuario(usuario);
   }

   @POST
   @Path("/removerUsuario")
   public boolean removerUsuario(UsuarioDTO usuario) throws CronosException
   {
      return getEntityService().removerUsuario(usuario);
   }

   @Override
   protected UsuarioService getEntityService()
   {
      return usuarioService;
   }

   @GET
   @Path("/findByGrupoTrabalho/{idGrupoTrabalho}")
   public List<UsuarioDTO> findByGrupoTrabalho(@PathParam("idGrupoTrabalho") Long idGrupoTrabalho)
            throws CronosException
   {
      return getEntityService().findByGrupoTrabalho(idGrupoTrabalho);
   }

   @POST
   @Path("/aceiteTermo")
   public UsuarioEntity aceiteTermo(AceiteTermoDTO aceite) throws CronosException
   {
      return getEntityService().aceiteTermo(aceite);
   }

   @POST
   @Path("/mudaPermissoes")
   public UsuarioDTO mudaPermissoes(UsuarioDTO usuario) throws CronosException
   {
      return getEntityService().mudaPermissoes(usuario);
   }

}
