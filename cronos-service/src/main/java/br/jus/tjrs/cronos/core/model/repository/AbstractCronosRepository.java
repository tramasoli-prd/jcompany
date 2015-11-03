package br.jus.tjrs.cronos.core.model.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

public abstract class AbstractCronosRepository<PK extends Serializable, E extends VersionedEntity<PK>> extends AbstractRepository<PK, E>
{
   @PersistenceContext(unitName = "cronos-persistence-unit")
   private EntityManager entityManager;

   @Override
   protected EntityManager getEntityManager()
   {
      return entityManager;
   }
}
