package com.powerlogic.jcompany.commons.util.validation;

import javax.xml.bind.annotation.XmlRootElement;

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
