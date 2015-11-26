package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Local;

import com.powerlogic.jcompany.core.model.service.IPlcEntityService;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FuncionarioEntity;

@Local
public interface FuncionarioService extends IPlcEntityService<Long, FuncionarioEntity> {

	FotoEntity getFoto(Long idFoto);
   

}
