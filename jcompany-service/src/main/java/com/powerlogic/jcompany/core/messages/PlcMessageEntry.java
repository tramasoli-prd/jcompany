/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.messages;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlcMessageEntry that = (PlcMessageEntry) o;

		return (key == that.key) && (type == that.type) && ((message != null) ? message.equals(that.message) :
				((that.message == null) && Objects.deepEquals(args, that.args)));

	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (message != null ? message.hashCode() : 0);
		result = 31 * result + Arrays.hashCode(args);
		return result;
	}
}
