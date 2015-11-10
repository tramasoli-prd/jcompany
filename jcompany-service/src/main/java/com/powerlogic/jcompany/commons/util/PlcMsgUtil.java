package com.powerlogic.jcompany.commons.util;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.exception.PlcBeanMessages;
import com.powerlogic.jcompany.core.messages.PlcMessageEntry;
import com.powerlogic.jcompany.core.messages.PlcMessageKey;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.messages.PlcMessageType;

@RequestScoped
public class PlcMsgUtil {

	
	protected PlcMessageMap mensagens = new PlcMessageMap();
	

	public PlcMessageMap getMensagens() {
		return mensagens;
	}


	public void msg(PlcMessageKey key, PlcMessageType type, String... args)  {
		
		try {
			
			List<PlcMessageEntry> listaMensagens = mensagens.get(type);
			
			if (listaMensagens == null){
				listaMensagens = new ArrayList<PlcMessageEntry>();
			}
			
			mensagens.add( new PlcMessageEntry(key, type, args) );
			
		} catch(PlcException plcE){
			throw plcE;			
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_OPERACAO_003.create();
		}
	}

}
