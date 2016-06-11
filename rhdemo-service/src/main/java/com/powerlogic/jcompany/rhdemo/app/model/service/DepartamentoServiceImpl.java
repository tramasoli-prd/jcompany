package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.DepartamentoRepository;

@Stateless
@Interceptors({PlcValidationInterceptor.class})
public class DepartamentoServiceImpl extends PlcAbstractServiceEntity<Long, DepartamentoEntity> implements IDepartamentoService {
	
	@Inject
	private DepartamentoRepository departamentoRepository;


	@Override
	protected IPlcEntityRepository<Long, DepartamentoEntity> getEntityRepository() {
		return departamentoRepository;
	}
}
