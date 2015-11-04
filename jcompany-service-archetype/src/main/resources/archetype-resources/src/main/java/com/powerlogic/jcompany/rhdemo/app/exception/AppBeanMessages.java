#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package com.powerlogic.jcompany.rhdemo.app.exception;

import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.messages.PlcMessageKey;

public enum AppBeanMessages implements PlcMessageKey
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
