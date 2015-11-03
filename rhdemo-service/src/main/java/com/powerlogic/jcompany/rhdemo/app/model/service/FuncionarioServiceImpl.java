package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;

import com.powerlogic.jcompany.commons.util.PlcFileDTO;
import com.powerlogic.jcompany.commons.util.PlcFileUploadUtil;
import com.powerlogic.jcompany.commons.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.model.repository.PlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoConteudoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FuncionarioEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.FuncionarioRepository;

@Stateless
@Interceptors ({PlcValidationInterceptor.class})
public class FuncionarioServiceImpl extends PlcAbstractServiceEntity<Long, FuncionarioEntity> implements FuncionarioService {

	@Inject
	private FuncionarioRepository funcionarioRepository;


	@Override
	protected PlcEntityRepository<Long, FuncionarioEntity> getEntityRepository() {
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
