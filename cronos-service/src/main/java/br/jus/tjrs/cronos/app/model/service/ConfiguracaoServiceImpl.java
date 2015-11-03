package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.dto.ConfiguracaoDTO;
import br.jus.tjrs.cronos.app.model.entity.ConfiguracaoEntity;
import br.jus.tjrs.cronos.app.model.repository.ConfiguracaoRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class ConfiguracaoServiceImpl extends AbstractServiceEntity<Long, ConfiguracaoEntity> implements
         ConfiguracaoService
{

   @Inject
   private ConfiguracaoRepository configuracaoRepository;

   @Override
   protected ConfiguracaoRepository getEntityRepository()
   {
      return configuracaoRepository;
   }

   @Override
   public ConfiguracaoEntity permitirEditarMenu(ConfiguracaoDTO configurar) throws CronosException
   {
      ConfiguracaoEntity configurarNovo = findConfiguracaoByGrupoTrabalho(configurar.getIdGrupoTrabalho());
      configurarNovo.setUsuarioAtualizacao(configurar.getUsuarioAtualizacao());
      configurarNovo.setPermiteEditarMenu(configurar.getPermiteEditarMenu());
      return save(configurarNovo);
   }

   @Override
   public ConfiguracaoEntity findConfiguracaoByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      return getEntityRepository().buscaConfiguracaoByGRupoTrabalho(idGrupoTrabalho);
   }
}
