package com.powerlogic.jcompany.core.bean;

import org.apache.commons.beanutils.converters.AbstractConverter;

/** Bean Converter para ENUMs
 */
public class PlcEnumBeanConverter extends AbstractConverter {

    @SuppressWarnings("rawtypes")
	@Override
    protected String convertToString(final Object pValue) throws Throwable {
        return ((Enum) pValue).name();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    protected Object convertToType(final Class pType, final Object pValue) throws Throwable    {

        final Class<? extends Enum> type = pType;
        try {
            return Enum.valueOf(type, pValue.toString());
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
