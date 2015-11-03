package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.domain.Permissoes;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.dto.AceiteTermoDTO;
import br.jus.tjrs.cronos.app.model.dto.UsuarioDTO;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaUsuarioEntity;
import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity;
import br.jus.tjrs.cronos.app.model.repository.UsuarioRepository;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;
import br.jus.tjrs.cronos.core.rest.auth.AuthenticatedUserInfo;

@Stateless
public class UsuarioServiceImpl extends AbstractServiceEntity<Long, UsuarioEntity> implements UsuarioService
{
   @Inject
   private UsuarioRepository usuarioRepository;

   @Inject
   private TaxonomiaUsuarioService taxonomiaUsuarioService;

   @EJB
   private GrupoTrabalhoService grupoTrabalhoService;

   @Override
   public UsuarioEntity findByCpf(String cpf)
   {
      return usuarioRepository.findByCpf(cpf);
   }

   @Override
   public UsuarioEntity createFirstUserMagistrate(AuthenticatedUserInfo userInfo) throws CronosException
   {
      UsuarioEntity usuarioEntity = this.save(createUserMagistrate(userInfo));
      createFirstTreeView(usuarioEntity);
      return usuarioEntity;
   }

   protected void createFirstTreeView(UsuarioEntity usuarioEntity) throws CronosException
   {
      UsuarioEntity usuarioPadrao = usuarioRepository.findUsuarioPadrao();
      List<TaxonomiaUsuarioEntity> listaGrupoTrabalho = this.taxonomiaUsuarioService.findAllByUsuario(usuarioPadrao
               .getId());
      GrupoTrabalhoEntity grupoAntigo = listaGrupoTrabalho.get(0).getGrupoTrabalho();

      GrupoTrabalhoEntity grupoTrabalhoNovo = grupoTrabalhoService.createFirstTreeView(
               grupoAntigo.clone(usuarioEntity), grupoAntigo);
      TaxonomiaUsuarioEntity taxonomiaUsuario = new TaxonomiaUsuarioEntity();
      taxonomiaUsuario.setOrdem(1);
      taxonomiaUsuario.setIdGrupoTrabalho(grupoTrabalhoNovo.getId());
      taxonomiaUsuario.setIdUsuario(usuarioEntity.getId());
      taxonomiaUsuario.setPadrao(SimNao.S);
      taxonomiaUsuario.setPermissoes(Permissoes.A);
      this.taxonomiaUsuarioService.save(taxonomiaUsuario);
   }

   @Override
   protected UsuarioRepository getEntityRepository()
   {
      return usuarioRepository;
   }

   public UsuarioEntity createUserMagistrate(AuthenticatedUserInfo userInfo) throws CronosException
   {
      UsuarioEntity usuarioEntity = new UsuarioEntity();

      usuarioEntity.setCpf(userInfo.getCpf());
      usuarioEntity.setNome(userInfo.getName());
      usuarioEntity.setLogin(userInfo.getUsername());
      usuarioEntity.setUsuarioAtualizacao(userInfo.getUsername());
      usuarioEntity.setMagistrado(SimNao.S);
      usuarioEntity.setPermissoes(Permissoes.W);
      usuarioEntity.setPadrao(SimNao.N);
      usuarioEntity.setSituacao(Situacao.A);
      usuarioEntity.setUsuarioAtualizacao(ConstantUtil.USER_APPLICATION);
      return usuarioEntity;
   }

   @Override
   public UsuarioEntity findByLogin(String login) throws CronosException
   {
      return getEntityRepository().findByLogin(login);
   }

   @Override
   public UsuarioEntity aceiteTermo(AceiteTermoDTO aceite) throws CronosException
   {
      UsuarioEntity usuarioEntity = get(aceite.getId());
      usuarioEntity.setAceiteTermo(SimNao.S);
      usuarioEntity.setUsuarioAtualizacao(aceite.getUsuarioAtualizacao());
      return save(usuarioEntity);
   }

   @Override
   public UsuarioDTO salvarUsuario(UsuarioDTO usuario) throws CronosException
   {
      UsuarioEntity usuarioNovo = findByCpf(usuario.getCpf());
      if (usuarioNovo == null)
      {
         usuarioNovo = new UsuarioEntity();
         usuarioNovo.setCpf(usuario.getCpf());
         usuarioNovo.setLogin(usuario.getLogin());
         usuarioNovo.setAceiteTermo(SimNao.N);
         usuarioNovo.setNome(usuario.getNome());
         usuarioNovo.setUsuarioAtualizacao(usuario.getUsuarioAtualizacao());
         usuarioNovo = save(usuarioNovo);
      }
      Integer maxOrderGrupo = this.taxonomiaUsuarioService.maxOrderGrupo(usuario.getIdGrupoTrabalho());
      Integer maxOrderUsuario = this.taxonomiaUsuarioService.maxOrderUsuario(usuarioNovo.getId());         

      TaxonomiaUsuarioEntity entity = new TaxonomiaUsuarioEntity();
      entity.setIdGrupoTrabalho(usuario.getIdGrupoTrabalho());
      entity.setIdUsuario(usuarioNovo.getId());
      entity.setOrdem(maxOrderGrupo + 1);
      if (maxOrderUsuario == null || maxOrderUsuario == 0 )
      {
         entity.setPadrao(SimNao.S);
      }
      else
      {
         entity.setPadrao(SimNao.N);
      }
      entity.setPermissoes(Permissoes.R);
      entity.setUsuarioAtualizacao(usuario.getUsuarioAtualizacao());
      this.taxonomiaUsuarioService.save(entity);

      usuario.setMagistrado(usuarioNovo.getMagistrado());
      usuario.setAceiteTermo(usuarioNovo.getAceiteTermo());
      usuario.setVersao(usuarioNovo.getVersao());
      usuario.setPermissoes(entity.getPermissoes());
      usuario.setId(usuarioNovo.getId());
      return usuario;
   }

   @Override
   public List<UsuarioDTO> findByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      List<TaxonomiaUsuarioEntity> findAllByGrupoTrabalho = this.taxonomiaUsuarioService
               .findAllByGrupoTrabalho(idGrupoTrabalho);
      List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
      for (TaxonomiaUsuarioEntity taxonomiaUsuarioEntity : findAllByGrupoTrabalho)
      {
         UsuarioEntity usuario = taxonomiaUsuarioEntity.getUsuario();
         if (SimNao.N.equals(usuario.getMagistrado()))
         {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setCpf(usuario.getCpf());
            usuarioDTO.setAceiteTermo(usuario.getAceiteTermo());
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setIdGrupoTrabalho(taxonomiaUsuarioEntity.getIdGrupoTrabalho());
            usuarioDTO.setLogin(usuario.getLogin());
            usuarioDTO.setMagistrado(usuario.getMagistrado());
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setPermissoes(taxonomiaUsuarioEntity.getPermissoes());
            listaUsuarios.add(usuarioDTO);
         }
      }
      Collections.sort(listaUsuarios, new Comparator<UsuarioDTO>()
      {
         @Override
         public int compare(UsuarioDTO obj1, UsuarioDTO obj2)
         {
            return obj1.getNome().compareTo(obj2.getNome());
         }
      });
      return listaUsuarios;
   }

   @Override
   public boolean removerUsuario(UsuarioDTO usuario) throws CronosException
   {
      UsuarioEntity usuarioNovo = get(usuario.getId());
      List<TaxonomiaUsuarioEntity> listaUsuarios = this.taxonomiaUsuarioService.findAllByUsuario(usuario.getId());
      if (listaUsuarios.size() == 1)
      {
         remove(usuarioNovo);
      }
      for (TaxonomiaUsuarioEntity taxonomiaUsuarioEntity : listaUsuarios)
      {
         if (usuario.getIdGrupoTrabalho().equals(taxonomiaUsuarioEntity.getIdGrupoTrabalho()))
         {
            this.taxonomiaUsuarioService.remove(taxonomiaUsuarioEntity);
            break;
         }
      }
      return true;
   }

   @Override
   public UsuarioDTO mudaPermissoes(UsuarioDTO usuario) throws CronosException
   {
      List<TaxonomiaUsuarioEntity> listaUsuarios = this.taxonomiaUsuarioService.findAllByUsuarioGrupoTrabalho(
               usuario.getId(), usuario.getIdGrupoTrabalho());
      TaxonomiaUsuarioEntity taxonomiaUsuarioEntity = listaUsuarios.get(0);
      taxonomiaUsuarioEntity.setPermissoes(usuario.getPermissoes());
      this.taxonomiaUsuarioService.save(taxonomiaUsuarioEntity);
      return usuario;
   }
}
