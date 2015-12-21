/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.util.validation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Bean para encapsular as propriedades e contraint 
 * 
 * Propagar informação para Front-End
 * 
 * Unificar a validação das propriedades do Form com base na entidade que a representa no Back-End.
 * 
 * @category DTO
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@XmlRootElement
public class PlcValidationPropertyConstraintsDTO {

	private String property;
	
	private Object constraint;
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getConstraint() {
		return constraint;
	}

	public void setConstraint(Object constraint) {
		this.constraint = constraint;
	}

	
}
