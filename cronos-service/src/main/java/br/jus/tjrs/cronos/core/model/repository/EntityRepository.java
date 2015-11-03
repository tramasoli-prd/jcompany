package br.jus.tjrs.cronos.core.model.repository;

import java.io.Serializable;
import java.util.List;

import br.jus.tjrs.cronos.commons.search.PagedResult;
import br.jus.tjrs.cronos.commons.search.Pagination;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;
import br.jus.tjrs.cronos.core.model.repository.specification.Specification;

public interface EntityRepository<PK extends Serializable, E extends VersionedEntity<PK>>
{
   Class<E> getEntityType();

   List<E> findAll() throws CronosException;

   List<E> findAll(Specification<E> specification) throws CronosException;

   PagedResult<E> find(Pagination<E> config);

   PagedResult<E> find(Specification<E> specification, Pagination<E> config);

   E get(PK id);

   E getDetached(PK id);

   E save(E entity) throws CronosException;

   void remove(E entity);
   
   void inative(E entity);

}