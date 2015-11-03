package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;

import br.jus.tjrs.cronos.app.model.entity.TipoAlegacaoEntity;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TipoAlegacaoRepository extends AbstractCronosRepository<Long, TipoAlegacaoEntity>
{
   @Override
   public Class<TipoAlegacaoEntity> getEntityType()
   {
      return TipoAlegacaoEntity.class;
   }
}
