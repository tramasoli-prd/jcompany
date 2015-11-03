package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;

import br.jus.tjrs.cronos.app.model.entity.TipoElementoEntity;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TipoElementoRepository extends AbstractCronosRepository<Long, TipoElementoEntity>
{
   @Override
   public Class<TipoElementoEntity> getEntityType()
   {
      return TipoElementoEntity.class;
   }
}
