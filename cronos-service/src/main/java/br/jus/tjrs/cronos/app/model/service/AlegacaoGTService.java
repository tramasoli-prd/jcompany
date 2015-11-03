package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import br.jus.tjrs.cronos.app.model.entity.AlegacaoGTEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

public interface AlegacaoGTService extends EntityService<Long, AlegacaoGTEntity>
{
   List<AlegacaoGTEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException;
}