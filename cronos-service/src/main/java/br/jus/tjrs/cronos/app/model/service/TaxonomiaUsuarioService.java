package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaUsuarioEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface TaxonomiaUsuarioService extends EntityService<Long, TaxonomiaUsuarioEntity>
{
   List<TaxonomiaUsuarioEntity> findAllByUsuario(Long idUsuario) throws CronosException;

   List<TaxonomiaUsuarioEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException;

   Integer maxOrderGrupo(Long idGrupo) throws CronosException;

   Integer maxOrderUsuario(Long idUsuario) throws CronosException;

   List<TaxonomiaUsuarioEntity> findAllByUsuarioGrupoTrabalho(Long idUsuario, Long idGrupoTrabalho)
            throws CronosException;
}
