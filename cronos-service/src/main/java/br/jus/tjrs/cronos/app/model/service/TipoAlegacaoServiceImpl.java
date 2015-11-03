package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TipoAlegacaoEntity;
import br.jus.tjrs.cronos.app.model.repository.TipoAlegacaoRepository;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class TipoAlegacaoServiceImpl extends AbstractServiceEntity<Long, TipoAlegacaoEntity> implements
         TipoAlegacaoService
{
   @Inject
   private TipoAlegacaoRepository tipoAlegacaoRepository;

   @Override
   protected TipoAlegacaoRepository getEntityRepository()
   {
      return tipoAlegacaoRepository;
   }

}
