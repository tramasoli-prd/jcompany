package com.powerlogic.jcompany.rhdemo.app.model.repository;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.repository.PlcAbstractRepository;
import com.powerlogic.jcompany.rhdemo.app.model.entity.UnidadeFederativaEntity;

public class UnidadeFederativaRepository extends PlcAbstractRepository<Long, UnidadeFederativaEntity> {
	
	public UnidadeFederativaEntity save(UnidadeFederativaEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	public Class<UnidadeFederativaEntity> getEntityType() {
		return UnidadeFederativaEntity.class;
	}

	public UnidadeFederativaEntity findByNome(String nome) {
		return null;
	}

}