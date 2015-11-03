package br.jus.tjrs.cronos.core.model.service;

import java.io.Serializable;
import java.util.List;

import br.jus.tjrs.cronos.commons.search.PagedResult;
import br.jus.tjrs.cronos.commons.search.Pagination;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.entity.EntityModel;

public interface EntityService<PK extends Serializable, E extends EntityModel<PK>>
{
   List<E> findAll() throws CronosException;

   PagedResult<E> find(Pagination<E> config) throws CronosException;

   E newEntity();

   E get(PK id);

   E save(E entity) throws CronosException;

   void remove(E entity) throws CronosException;
   
   void inative(E entity) throws CronosException;
}
