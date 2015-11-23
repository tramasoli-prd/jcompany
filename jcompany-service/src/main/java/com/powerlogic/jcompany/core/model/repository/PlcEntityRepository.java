package com.powerlogic.jcompany.core.model.repository;

import java.io.Serializable;
import java.util.List;

import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.commons.search.PlcPagination;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.entity.PlcEntityModel;

public interface PlcEntityRepository<PK extends Serializable, E extends PlcEntityModel<PK>> {
	
   Class<E> getEntityType();

   List<E> findAll() throws PlcException;
   
   List<E> findAll(E entity) throws PlcException;

   PlcPagedResult<E> find(PlcPagination<E> config);

   E get(PK id);

   E getDetached(PK id);

   E save(E entity) throws PlcException;

   void remove(E entity);
   
   void inative(E entity);

}