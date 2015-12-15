package com.powerlogic.jcompany.model.test;

import javax.enterprise.context.ApplicationScoped;

import com.powerlogic.jcompany.core.model.repository.PlcAbstractRepository;

/**
 * Repository for "Funcionario"
 *
 * @author jCompany Generator
 *
 */

@ApplicationScoped
public class UserRepository extends PlcAbstractRepository<Long, UserEntity> {

	@Override
	public Class<UserEntity> getEntityType() {
		return UserEntity.class;
	}

}
