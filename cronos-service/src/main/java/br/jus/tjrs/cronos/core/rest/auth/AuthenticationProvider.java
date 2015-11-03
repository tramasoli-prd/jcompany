package br.jus.tjrs.cronos.core.rest.auth;

import java.security.Principal;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.dto.UsuarioDTO;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaUsuarioEntity;
import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity;
import br.jus.tjrs.cronos.app.model.service.TaxonomiaUsuarioService;
import br.jus.tjrs.cronos.app.model.service.UsuarioService;
import br.jus.tjrs.cronos.core.CronosException;

@ApplicationScoped
public class AuthenticationProvider
{
   @Inject
   private WeblogicIntegration weblogicIntegration;

   @Inject
   private UsuarioService usuarioService;

   @Inject
   private TaxonomiaUsuarioService taxonomiaUsuarioService;

   // REALY???
   // private PlcJSecurityIntegration jsecurityIntegration;

   public AuthenticatedUserInfo createUser(final Principal userPrincipal, final String host) throws CronosException
   {
      AuthenticatedUserInfo user = new AuthenticatedUserInfo(userPrincipal.getName(), host,
               weblogicIntegration.getRoles());
      if (!weblogicIntegration.loadExtraInfo(user, userPrincipal))
      {
         throw CronosErrors.USUARIO_SEM_PERMISSAO_005.create();
      }

      UsuarioEntity usuarioEntity = usuarioService.findByCpf(user.getCpf());
      if (usuarioEntity == null)
      {
         if (!user.isMagistrado())
         {
            throw CronosErrors.USUARIO_SEM_PERMISSAO_005.create();
         }
         usuarioEntity = this.usuarioService.createFirstUserMagistrate(user);
      }
      if (!usuarioEntity.getLogin().equalsIgnoreCase(user.getUsername()))
      {
         usuarioEntity.setLogin(user.getUsername());
         usuarioService.save(usuarioEntity);
      }
      TaxonomiaUsuarioEntity taxonomiaUsuario = carregarGrupoTrabalho(usuarioEntity);
      GrupoTrabalhoEntity grupoTrabalho = taxonomiaUsuario.getGrupoTrabalho();
      user.setIdGrupoTrabalho(grupoTrabalho.getId());

      UsuarioDTO usuario = createUsuarioDTO(usuarioEntity, taxonomiaUsuario);
      user.setUsuario(usuario);
      return user;
   }

   private UsuarioDTO createUsuarioDTO(UsuarioEntity usuarioEntity, TaxonomiaUsuarioEntity taxonomiaUsuario)
   {
      UsuarioDTO usuario = new UsuarioDTO();
      usuario.setId(usuarioEntity.getId());
      usuario.setIdPai(usuarioEntity.getIdPai());
      usuario.setCpf(usuarioEntity.getCpf());
      usuario.setNome(usuarioEntity.getNome());
      usuario.setPermissoes(taxonomiaUsuario.getPermissoes());
      usuario.setMagistrado(usuarioEntity.getMagistrado());
      usuario.setVersao(usuarioEntity.getVersao());
      usuario.setLogin(usuarioEntity.getLogin());
      usuario.setAceiteTermo(usuarioEntity.getAceiteTermo());
      return usuario;
   }

   public AuthenticatedUserInfo changeUser(AuthenticatedUserInfo user)
            throws CronosException
   {
      if (user == null || user.getUsuario() == null)
      {
         return null;
      }
      UsuarioEntity usuarioEntity = this.usuarioService.get(user.getUsuario().getId());
      if (usuarioEntity == null)
      {
         return null;
      }
      TaxonomiaUsuarioEntity taxonomiaUsuario = carregarGrupoTrabalho(usuarioEntity);
      GrupoTrabalhoEntity grupoTrabalho = taxonomiaUsuario.getGrupoTrabalho();
      user.setIdGrupoTrabalho(grupoTrabalho.getId());

      UsuarioDTO usuario = createUsuarioDTO(usuarioEntity, taxonomiaUsuario);
      user.setUsuario(usuario);
      return user;
   }

   private TaxonomiaUsuarioEntity carregarGrupoTrabalho(UsuarioEntity usuarioEntity) throws CronosException
   {
      List<TaxonomiaUsuarioEntity> listaTaxonomiaUsuario = this.taxonomiaUsuarioService.findAllByUsuario(usuarioEntity.getId());
      TaxonomiaUsuarioEntity taxonomiaUsuario = null;
      for (TaxonomiaUsuarioEntity taxonomiaUsuarioEntity : listaTaxonomiaUsuario)
      {
         if (SimNao.S.equals(taxonomiaUsuarioEntity.getPadrao()))
         {
            taxonomiaUsuario = taxonomiaUsuarioEntity;
            break;
         }
      }
      if (taxonomiaUsuario == null) 
      {
         throw CronosErrors.USUARIO_SEM_PERMISSAO_005.create();
      }
      return taxonomiaUsuario;
   }
}
