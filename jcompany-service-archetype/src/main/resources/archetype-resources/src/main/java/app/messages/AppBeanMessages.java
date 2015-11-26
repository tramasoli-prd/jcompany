#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.messages;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.IPlcMessageKey;

public enum AppBeanMessages implements IPlcMessageKey
{
	TESTE_MESSAGE_ERROR_APP_01;

	public String getName()
	{
		return name();
	}

	public PlcException create()
	{
		return new PlcException(this);
	}

	public PlcException create(String... args)
	{
		return new PlcException(this, args);
	}

}
