package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;

import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FuncionarioEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.FuncionarioRepository;

@Stateless
@Interceptors ({PlcValidationInterceptor.class})
public class FuncionarioServiceImpl extends PlcAbstractServiceEntity<Long, FuncionarioEntity> implements FuncionarioService {

	@Inject
	private FuncionarioRepository funcionarioRepository;


	@Override
	protected IPlcEntityRepository<Long, FuncionarioEntity> getEntityRepository() {
		return funcionarioRepository;
	}

	@Override
	public FuncionarioEntity save(@Valid FuncionarioEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	public FotoEntity getFoto(Long idFoto) {
		return funcionarioRepository.getFoto(idFoto);
	}
}
