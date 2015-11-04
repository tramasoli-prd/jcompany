#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;

import com.powerlogic.jcompany.commons.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.model.repository.PlcEntityRepository;
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
	protected PlcEntityRepository<Long, UnidadeFederativaEntity> getEntityRepository() {
		return unidadeFederativaRepository;
	}

	
	@Override
	public UnidadeFederativaEntity save(@Valid UnidadeFederativaEntity entity) throws PlcException {
		return super.save(entity);
	}
	
}
