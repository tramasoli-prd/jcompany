package com.powerlogic.jcompany.core.messages;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlcMessageEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private IPlcMessageKey key;

	private PlcMessageType type;

	private String message;

	private String[] args;

	public PlcMessageEntry(IPlcMessageKey key, PlcMessageType type)
	{
		this.key = key;
		this.type = type;
	}

	public PlcMessageEntry(IPlcMessageKey key, PlcMessageType type, String... args)
	{
		this(key, type);
		this.args = args;
	}

	public PlcMessageType getType()
	{
		return type;
	}

	public IPlcMessageKey getKey()
	{
		return key;
	}

	public String getMessage()
	{
		if (message != null && !message.isEmpty())
		{
			return message;
		}
		return key.getName();
	}

	void setMessage(String message)
	{
		this.message = message;
	}

	public String[] getArgs()
	{
		return args;
	}

	void setArgs(String[] args)
	{
		this.args = args;
	}

	@Override
	public String toString()
	{
		return getMessage();
	}


	@Override
	public int hashCode() {
		return key.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlcMessageEntry other = (PlcMessageEntry) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.getName().equals(other.getKey().getName())) {
			return false;
		}
		return true;
	}
}
