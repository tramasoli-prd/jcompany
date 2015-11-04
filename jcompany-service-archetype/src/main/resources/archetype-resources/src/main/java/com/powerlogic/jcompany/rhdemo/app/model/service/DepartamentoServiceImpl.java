#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.model.repository.PlcEntityRepository;
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
	protected PlcEntityRepository<Long, DepartamentoEntity> getEntityRepository() {
		return departamentoRepository;
	}
}
