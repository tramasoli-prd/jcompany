package br.jus.tjrs.cronos.core.rest.entity;

import java.io.Serializable;
import java.util.List;

import br.jus.tjrs.cronos.commons.search.PagedResult;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.entity.EntityModel;
import br.jus.tjrs.cronos.core.model.service.EntityService;
import br.jus.tjrs.cronos.core.rest.AbstractRest;

public abstract class AbstractEntityRest<PK extends Serializable, E extends EntityModel<PK>, A> extends AbstractRest
         implements EntityModelRest<PK, E, A>
{
   protected abstract EntityService<PK, E> getEntityService();

   protected List<E> findAll() throws CronosException
   {
      return getEntityService().findAll();
   }

   @Override
   public E newEntity() throws CronosException
   {
      return getEntityService().newEntity();
   }

   @Override
   public E get(PK entityId) throws CronosException
   {
      return getEntityService().get(entityId);
   }

   @Override
   public E save(E entity) throws CronosException
   {
      return getEntityService().save(entity);
   }

   @Override
   public boolean remove(E entity) throws CronosException
   {
      getEntityService().remove(entity);
      return true;
   }

   @Override
   public PagedResult<E> find(A searchBuilder) throws CronosException
   {
      throw new UnsupportedOperationException();
   }
}
