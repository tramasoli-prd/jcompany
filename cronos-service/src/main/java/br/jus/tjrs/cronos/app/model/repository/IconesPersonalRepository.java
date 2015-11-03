package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;

import br.jus.tjrs.cronos.app.model.entity.IconesPersonalEntity;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class IconesPersonalRepository extends AbstractCronosRepository<Long, IconesPersonalEntity>
{
   @Override
   public Class<IconesPersonalEntity> getEntityType()
   {
      return IconesPersonalEntity.class;
   }
}
