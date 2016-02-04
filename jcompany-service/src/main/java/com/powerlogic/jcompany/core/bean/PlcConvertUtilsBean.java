package com.powerlogic.jcompany.core.bean;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/** Util para coversao de beans
 */
public class PlcConvertUtilsBean extends ConvertUtilsBean2 {

    private static final PlcEntityModelBeanConverter PLC_ENTITY_MODEL_CONVERTER = new PlcEntityModelBeanConverter();
    private static final PlcEnumBeanConverter PLC_ENUM_CONVERTER = new PlcEnumBeanConverter();

    @SuppressWarnings("rawtypes")
	@Override
    public Converter lookup(Class pClazz) {
    	
        final Converter converter = super.lookup(pClazz);

        if (Date.class.isAssignableFrom(pClazz)) {
        	DateTimeConverter dtConverter = new DateConverter();
        	dtConverter.setPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return dtConverter;
        } else if (converter == null && IPlcEntityModel.class.isAssignableFrom(pClazz)) {
            return PLC_ENTITY_MODEL_CONVERTER;
        } else if (converter == null && pClazz.isEnum()) {
            return PLC_ENUM_CONVERTER;
        } else {
            return converter;
        }
    }

}
