package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Local;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.service.IPlcEntityService;
import com.powerlogic.jcompany.rhdemo.app.model.entity.UnidadeFederativaEntity;

@Local
public interface UnidadeFederativaService extends IPlcEntityService <Long, UnidadeFederativaEntity> {
   
	UnidadeFederativaEntity findByNome(String nome) throws PlcException;


}
