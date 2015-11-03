package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;

import br.jus.tjrs.cronos.app.model.entity.TipoCategoriaEntity;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TipoCategoriaRepository extends AbstractCronosRepository<Long, TipoCategoriaEntity>
{
   @Override
   public Class<TipoCategoriaEntity> getEntityType()
   {
      return TipoCategoriaEntity.class;
   }
}
