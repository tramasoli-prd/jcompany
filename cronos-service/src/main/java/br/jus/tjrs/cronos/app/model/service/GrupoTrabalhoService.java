package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoLoginDTO;
import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoNodoDTO;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface GrupoTrabalhoService extends EntityService<Long, GrupoTrabalhoEntity>
{
   GrupoTrabalhoEntity createFirstTreeView(GrupoTrabalhoEntity grupoTrabalhoNovo,
            GrupoTrabalhoEntity grupoTrabalhoAntigo) throws CronosException;

   GrupoTrabalhoNodoDTO findTreeViewDTO(final Long idGrupoTrabalho) throws CronosException;

   List<GrupoTrabalhoLoginDTO> findByUsuario(Long idUsuario) throws CronosException;

   boolean alteraPadrao(GrupoTrabalhoLoginDTO grupo) throws CronosException;
}
