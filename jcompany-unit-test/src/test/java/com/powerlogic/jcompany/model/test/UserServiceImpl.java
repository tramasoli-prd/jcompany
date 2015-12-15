package com.powerlogic.jcompany.model.test;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;

import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;

/**
 * EJB Service for "Funcionario"
 *
 * @author jCompany Generator
 *
 */

@Stateless
@Interceptors ({PlcValidationInterceptor.class})
public class UserServiceImpl extends PlcAbstractServiceEntity<Long,  UserEntity> implements  IUserService {

	@Inject
	private UserRepository userRepository;


	@Override
	protected IPlcEntityRepository<Long, UserEntity> getEntityRepository() {
		return userRepository;
	}

	@Override
	public UserEntity save(@Valid UserEntity entity) throws PlcException {
		return super.save(entity);
	}

}


