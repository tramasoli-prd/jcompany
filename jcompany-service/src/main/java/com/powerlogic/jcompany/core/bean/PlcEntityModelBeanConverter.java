package com.powerlogic.jcompany.core.bean;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.converters.AbstractConverter;

import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/** Bean Converter para entidades
 */
public class PlcEntityModelBeanConverter extends AbstractConverter {

	@SuppressWarnings("unchecked")
	@Override
	protected String convertToString(final Object pValue) throws Throwable {
		if (((IPlcEntityModel<Long>)pValue).getId() != null ){
			return ((IPlcEntityModel<Long>)pValue).getId().toString();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object convertToType(final Class pType, final Object pValue) throws Throwable {
		try {
			Object bean = pType.newInstance();
			BeanUtils.setProperty(bean, "id", Long.parseLong(pValue.toString()));
			return bean;
		} catch (final IllegalArgumentException e) {
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Class getDefaultType() {
		return null;
	}

}
