package com.powerlogic.jcompany.core.rest.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;

import com.powerlogic.jcompany.commons.util.message.PlcMsgUtil;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationConstraintsDTO;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationInvariantUtil;
import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.messages.PlcMessageType;
import com.powerlogic.jcompany.core.model.entity.PlcEntityModel;
import com.powerlogic.jcompany.core.model.service.PlcEntityService;
import com.powerlogic.jcompany.core.rest.PlcAbstractRest;

public abstract class PlcAbstractEntityRest <PK extends Serializable, E extends PlcEntityModel<PK>, A>
extends PlcAbstractRest implements PlcEntityModelRest<PK, E, A> {

	protected abstract PlcEntityService<PK, E> getEntityService();

	@Inject
	private PlcMsgUtil msgUtil;

	@Inject
	private PlcValidationInvariantUtil validationInvariantUtil;

	protected List<E> findAll() throws PlcException {
		List<E> lista = getEntityService().findAll();
		if (lista==null || lista.size()==0) {
			msgUtil.msg(PlcBeanMessages.NENHUM_REGISTRO_ENCONTRADO_022, PlcMessageType.INFO);
		}
		return lista;
	}

	@Override
	public E newEntity() throws PlcException {
		return getEntityService().newEntity();
	}

	@Override
	public E get(PK entityId) throws PlcException {
		return getEntityService().get(entityId);
	}

	@Override
	public E save(E entity) throws PlcException {
		E e = getEntityService().save(entity);
		msgUtil.msg(PlcBeanMessages.DADOS_SALVOS_SUCESSO_000, PlcMessageType.SUCCESS); 
		return e; 
	}

	@Override
	public boolean remove(E entity) throws PlcException {
		getEntityService().remove(entity);
		msgUtil.msg(PlcBeanMessages.REGISTRO_EXCLUIDO_SUCESSO_021, PlcMessageType.SUCCESS); 
		return true;
	}

	@Override
	public PlcPagedResult<E> find(A searchBuilder) throws PlcException {
		throw new UnsupportedOperationException();
	}

	protected void setMasterIntoDetails(Object entity, List list, String property) {
		if (list!=null) {
			for(Object o: list) {
				try {
					Field f = o.getClass().getDeclaredField(property);
					f.setAccessible(true);
					f.set(o, entity);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public PlcValidationConstraintsDTO getEntityBeanValidationMetadata(){

		try {
			ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();

			return validationInvariantUtil.getConstraintsForClass((Class)pt.getActualTypeArguments()[1]);

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}

}