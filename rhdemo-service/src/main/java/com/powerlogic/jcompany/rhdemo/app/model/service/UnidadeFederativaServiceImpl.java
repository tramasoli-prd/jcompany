package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;

import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.UnidadeFederativaEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.UnidadeFederativaRepository;

@Stateless
@Interceptors ({PlcValidationInterceptor.class})
public class UnidadeFederativaServiceImpl extends PlcAbstractServiceEntity<Long, UnidadeFederativaEntity> implements UnidadeFederativaService {
	
	@Inject
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Override
	public UnidadeFederativaEntity findByNome(String nome) {
		return unidadeFederativaRepository.findByNome(nome);
	}


	@Override
	protected IPlcEntityRepository<Long, UnidadeFederativaEntity> getEntityRepository() {
		return unidadeFederativaRepository;
	}

	
	@Override
	public UnidadeFederativaEntity save(@Valid UnidadeFederativaEntity entity) throws PlcException {
		return super.save(entity);
	}
	
}
