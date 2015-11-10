package com.powerlogic.jcompany.core.rest.messages;

import javax.xml.bind.annotation.XmlRootElement;

import com.powerlogic.jcompany.core.messages.PlcMessageMap;

@XmlRootElement
public class PlcResponseEntity {

	private Object entity;
	
	private PlcMessageMap messageMap;

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public PlcMessageMap getMessageMap() {
		return messageMap;
	}

	public void setMessageMap(PlcMessageMap messageMap) {
		this.messageMap = messageMap;
	}
	
	
}
