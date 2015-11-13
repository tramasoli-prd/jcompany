package com.powerlogic.jcompany.commons.util.validation;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

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
