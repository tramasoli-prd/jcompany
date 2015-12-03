package com.powerlogic.jcompany.core.model.service;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.commons.search.PlcPagination;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

public interface IPlcEntityService<PK extends Serializable, E extends IPlcEntityModel<PK>> {
	
   List<E> findAll() throws PlcException;
   
   List<E> findAll(E entity) throws PlcException;

   PlcPagedResult<E> find(PlcPagination<E> config) throws PlcException;

   E newEntity();

   E get(PK id);

   E save(@Valid E entity) throws PlcException;

   void remove(E entity) throws PlcException;
   
   void inative(E entity) throws PlcException;
}