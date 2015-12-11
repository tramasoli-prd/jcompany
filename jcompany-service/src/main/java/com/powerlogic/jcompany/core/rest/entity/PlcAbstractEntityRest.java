package com.powerlogic.jcompany.core.rest.entity;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.lang3.StringUtils;

import com.powerlogic.jcompany.commons.util.message.PlcMsgUtil;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationConstraintsDTO;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationInvariantUtil;
import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.messages.PlcMessageType;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;
import com.powerlogic.jcompany.core.model.service.IPlcEntityService;
import com.powerlogic.jcompany.core.rest.PlcAbstractRest;

public abstract class PlcAbstractEntityRest <PK extends Serializable, E extends IPlcEntityModel<PK>, A>
extends PlcAbstractRest implements IPlcEntityModelRest<PK, E, A> {

	protected abstract IPlcEntityService<PK, E> getEntityService();

	@Inject
	private PlcMsgUtil msgUtil;

	@Inject
	private PlcValidationInvariantUtil validationInvariantUtil;

	protected BeanUtilsBean beanUtilsBean ;


	@GET
	@Path("/findAll")
	public List<E> findAll(@Context HttpServletRequest request, @Context UriInfo ui) throws PlcException {

		E entity = newEntity();

		try {

			MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
			Map<String, String[]> formData = new HashMap<String, String[]>();
			for (String paramName : queryParams.keySet()) {
				formData.put(paramName, new String[]{queryParams.getFirst(paramName)});
			}

			getBeanUtilsBean().populate(entity, formData);

		}catch(Exception e){
			throw new PlcException(PlcBeanMessages.FALHA_OPERACAO_003);
		}

		List<E> lista = getEntityService().findAll(entity);
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
		setEntityIntoCollections(entity);
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

	protected void setEntityIntoCollections(E entity) {
		String entityName = StringUtils.removeEnd(entity.getClass().getSimpleName(), "Entity").toLowerCase();
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entity);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType())) {
				try {
					setEntityIntoCollection(entity, (Collection)PropertyUtils.getProperty(entity, propertyDescriptor.getName()), entityName);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				}
			}
		}
	}

	protected void setEntityIntoCollection(Object entity, Collection<E> collection, String property) {
		if (collection!=null) {
			for(Object o: collection) {
				try {
					Field f = o.getClass().getDeclaredField(property);
					if (f!=null) {
						f.setAccessible(true);
						f.set(o, entity);
					}
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
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


	public BeanUtilsBean getBeanUtilsBean() {

		if (beanUtilsBean==null) {
			DateTimeConverter dtConverter = new DateConverter();
			dtConverter.setPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

			ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
			convertUtilsBean.deregister(Date.class);
			convertUtilsBean.register(dtConverter, Date.class);

			beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
		}
		return beanUtilsBean;
	}

	public void setBeanUtilsBean(BeanUtilsBean beanUtilsBean) {
		this.beanUtilsBean = beanUtilsBean;
	}

}