package com.powerlogic.jcompany.rhdemo.app.model.service;

import javax.ejb.Local;

import com.powerlogic.jcompany.core.model.service.IPlcEntityService;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;

@Local
public interface IDepartamentoService extends IPlcEntityService<Long, DepartamentoEntity> {
   
}
