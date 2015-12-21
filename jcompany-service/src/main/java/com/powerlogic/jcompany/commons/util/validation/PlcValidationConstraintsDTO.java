/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.util.validation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Bean que encapsula a lista de propriedades e constraints da camada de Back-End.
 * 
 * Essa lista será utilizada na camada de front-end, promovendo a "Cross Validation".
 * 
 * A validação das duas camadas se mantem integradas com base nas definições das entidades.
 * 
 * @category DTO
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@XmlRootElement
public class PlcValidationConstraintsDTO {

	private List<PlcValidationPropertyConstraintsDTO> properties;

	public List<PlcValidationPropertyConstraintsDTO> getProperties() {
		return properties;
	}

	public void setProperties(List<PlcValidationPropertyConstraintsDTO> properties) {
		this.properties = properties;
	}

	
	
}
