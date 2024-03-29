/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.service;

import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.commons.search.PlcPagination;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;
import com.powerlogic.jcompany.core.model.entity.IPlcVersionedEntity;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public abstract class PlcAbstractServiceEntity <PK extends Serializable, E extends IPlcEntityModel<PK>> implements IPlcEntityService<PK, E> {

	protected abstract IPlcEntityRepository<PK, E> getEntityRepository();

	@Override
	public E newEntity() {
		try {
			return getEntityRepository().getEntityType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Construtor padrão não existente...");
		}
	}

	@Override
	public List<E> findAll() throws PlcException {
		return getEntityRepository().findAll();
	}
	
	@Override
	public List<E> findAll(E entity) throws PlcException {
		return getEntityRepository().findAll(entity);
	}	

	@Override
	public PlcPagedResult<E> find(PlcPagination<E> config) throws PlcException {
		return getEntityRepository().find(config);
	}

	@Override
	public E get(PK id) {
		return getEntityRepository().get(id);
	}

	@Override
	public E save(@Valid E entity) throws PlcException {
		return getEntityRepository().save(entity);
	}

	@Override
	public void remove(E entity) throws PlcException {
		try {
			E novo = get(entity.getId());
	    	if (IPlcVersionedEntity.class.isAssignableFrom(entity.getClass())) {
	    		((IPlcVersionedEntity)novo).setUsuarioAtualizacao(((IPlcVersionedEntity)entity).getUsuarioAtualizacao());
	    		((IPlcVersionedEntity)novo).setVersao(((IPlcVersionedEntity)entity).getVersao());
	    	}
			getEntityRepository().remove(novo);
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_OPERACAO_003.create();
		}
	}

	@Override
	public void inative(E entity) throws PlcException {
		try {
			E novo = get(entity.getId());
    		((IPlcVersionedEntity)novo).setUsuarioAtualizacao(((IPlcVersionedEntity)entity).getUsuarioAtualizacao());
    		((IPlcVersionedEntity)novo).setVersao(((IPlcVersionedEntity)entity).getVersao());
			getEntityRepository().inative(novo);
		} catch (PlcException e) {
			throw e;			
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_OPERACAO_003.create();
		}
	}
}
