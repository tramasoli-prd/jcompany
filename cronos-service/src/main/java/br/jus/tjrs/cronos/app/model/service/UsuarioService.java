package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.dto.AceiteTermoDTO;
import br.jus.tjrs.cronos.app.model.dto.UsuarioDTO;
import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;
import br.jus.tjrs.cronos.core.rest.auth.AuthenticatedUserInfo;

@Local
public interface UsuarioService extends EntityService<Long, UsuarioEntity>
{
   UsuarioEntity findByCpf(String cpf) throws CronosException;

   UsuarioEntity createFirstUserMagistrate(AuthenticatedUserInfo userInfo) throws CronosException;

   UsuarioEntity findByLogin(String usuario) throws CronosException;

   UsuarioEntity aceiteTermo(AceiteTermoDTO aceite) throws CronosException;

   UsuarioDTO salvarUsuario(UsuarioDTO usuario) throws CronosException;

   List<UsuarioDTO> findByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException;

   boolean removerUsuario(UsuarioDTO usuario) throws CronosException;

   UsuarioDTO mudaPermissoes(UsuarioDTO usuario) throws CronosException;
}
