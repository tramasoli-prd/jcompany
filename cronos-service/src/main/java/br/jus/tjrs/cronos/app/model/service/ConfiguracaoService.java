package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.dto.ConfiguracaoDTO;
import br.jus.tjrs.cronos.app.model.entity.ConfiguracaoEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface ConfiguracaoService extends EntityService<Long, ConfiguracaoEntity>
{

   ConfiguracaoEntity permitirEditarMenu(ConfiguracaoDTO configurar) throws CronosException;

   ConfiguracaoEntity findConfiguracaoByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException;

}
