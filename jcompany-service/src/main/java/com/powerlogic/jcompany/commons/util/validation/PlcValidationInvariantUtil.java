/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.util.validation;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.powerlogic.jcompany.commons.util.interpolator.AggregateResourceBundleLocator;
import com.powerlogic.jcompany.commons.util.interpolator.ResourceBundleMessageInterpolator;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/**
 * 
 * Serviço dinâmicos para realização de validação invariante, vinculada ao modelo de domínio e podendo ser ativada em qualquer camada.
 * 
 * @category Util
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@ApplicationScoped
public class PlcValidationInvariantUtil {

	/**
	 * Validator: API Java EE
	 */
	protected Validator validator;

	/**
	 * Cria e Configura um Validator
	 * 
	 * Utiliza a classe ResourceBundleMessageInterpolator para fazer a manipulação dinâmica do conteúdo das Mensagens/
	 * 
	 * Os arquivos PlcMessages e AppMessages contém as chaves e valores das mensagens.
	 * 
	 * @return validator
	 */
	public Validator getValidator() {
		if (validator==null) {
			validator = Validation.byDefaultProvider()
					.configure()
					.messageInterpolator(
							new ResourceBundleMessageInterpolator(
									new AggregateResourceBundleLocator(
											Arrays.asList(
													"PlcMessages",
													"AppMessages"
													)
											)
									)
							)
					.buildValidatorFactory()
					.getValidator();
		}
		return validator;
	}

	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * Realiza validação invariante no padrão de Bean Validator
	 * 
	 * @param vo
	 * @param groups
	 * @return
	 */
	public <T> Set<ConstraintViolation<T>> invariantValidation(T vo, Class<?>... groups) {


		Set<ConstraintViolation<T>> ivs = null;
		// Valida efetivamente
		if (vo != null) {
			ivs =  getValidator().validate(vo, groups);
		}	

		return ivs;

	}

	/**
	 * Recupera as Constraints da classe que será validada. 
	 * 
	 * @param clazz
	 * @return constraints PlcValidationConstraintsDTO
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public PlcValidationConstraintsDTO getConstraintsForClass (Class clazz) throws NoSuchFieldException, SecurityException {


		PlcValidationConstraintsDTO constraints = new PlcValidationConstraintsDTO();

		BeanDescriptor beanDescriptor;

		if (clazz != null) {
			beanDescriptor =  getValidator().getConstraintsForClass(clazz);

			List<PlcValidationPropertyConstraintsDTO> properties = new ArrayList<PlcValidationPropertyConstraintsDTO>();
			for(PropertyDescriptor pd : beanDescriptor.getConstrainedProperties()){
				PlcValidationPropertyConstraintsDTO property = new PlcValidationPropertyConstraintsDTO();
				property.setProperty(pd.getPropertyName());

				if (Collection.class.isAssignableFrom(pd.getElementClass()) || Map.class.isAssignableFrom(pd.getElementClass())) {
					if (clazz.getDeclaredField(pd.getPropertyName()).getGenericType()!=null) {
						if (((ParameterizedType)clazz.getDeclaredField(pd.getPropertyName()).getGenericType()).getActualTypeArguments().length>0
								&& IPlcEntityModel.class.isAssignableFrom(((Class)((ParameterizedType)clazz.getDeclaredField(pd.getPropertyName()).getGenericType()).getActualTypeArguments()[0]))) {
							property.setConstraint(getConstraintsForClass((Class)((ParameterizedType)clazz.getDeclaredField(pd.getPropertyName()).getGenericType()).getActualTypeArguments()[0]));
						}
					}
				} else {
					Map<String,Map<String,String>> mapaConstraints = new HashMap<String,Map<String,String>>();
					for(ConstraintDescriptor cd : pd.getConstraintDescriptors()){
						String constraint = cd.getAnnotation().annotationType().getSimpleName();
						Map<String, Object> atributos = cd.getAttributes();
						Map<String, String> mapaConstraint = new HashMap<String, String>();
						for(String atributo : atributos.keySet()){
							if (!atributo.equals("message") && !atributo.equals("payload") && !atributo.equals("groups")){
								mapaConstraint.put(atributo, atributos.get(atributo).toString());
							}
						}
						mapaConstraints.put(constraint, mapaConstraint);
					}
					property.setConstraint(mapaConstraints);
				}
				properties.add(property);
			}

			constraints.setProperties(properties);


		}	
		return constraints;

	}

}
