package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.DepartamentoRepository;

@Stateless
public class DepartamentoServiceImpl extends PlcAbstractServiceEntity<Long, DepartamentoEntity> implements DepartamentoService {
	
	@Inject
	private DepartamentoRepository departamentoRepository;

	@Override
	public DepartamentoEntity findByRoot(Long id) throws PlcException {
		return null;
	}
	
	@Override
	public DepartamentoEntity save(@Valid DepartamentoEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	protected IPlcEntityRepository<Long, DepartamentoEntity> getEntityRepository() {
		return departamentoRepository;
	}
}
