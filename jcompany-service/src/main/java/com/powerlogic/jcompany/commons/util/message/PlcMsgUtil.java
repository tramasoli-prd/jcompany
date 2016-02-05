/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.util.message;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.IPlcMessageKey;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.messages.PlcMessageEntry;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.messages.PlcMessageType;

/**
 * 
 * Utilitário que encapsula as mensagens de erros no escopo da requisição.
 * 
 * Sempre que ocorre algum erro, se disparado através do utilitário, o response vai com os erros agrupados.
 * 
 * @category Util
 * @since 1.0.0
 * @author Powerlogic
 *
 */
@RequestScoped
public class PlcMsgUtil {

	
	protected PlcMessageMap mensagens = new PlcMessageMap();
	

	/**
	 * Retorna Mensagens agrupadas que ocorreram no nível da requisição.
	 * @return Mapa com as Mensgens que ocorreram no nível da requisição.
	 */
	public PlcMessageMap getMensagens() {
		return mensagens;
	}


	/**
	 * Método para disparo de uma nova mensagem. Será agrupadas com a demais, podendo ser de vários tipos:
	 * 
	 *	- SUCCESS: 	Mensagem de Sucesso 	- Cor Azul
	 *	- INFO:		Informações 			- Cor Verde
	 *	- WARNING 	Alerta 					- Cor Amarela
	 *	- ERROR: 	Mensagem de Erro 		- Cor Vermelha
	 *
	 * @param key 	- Chave com a mensagem a ser inserida
	 * @param type 	- PlcMessageType - Tipo da Mensagem
	 * @param args	- Argumentos (tokens) para troca dinâmica das informações
	 */
	public void msg(IPlcMessageKey key, PlcMessageType type, String... args)  {
		
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

	/**
	 * 
	 * Limpa a lista de mensagens já adicionadas na pilha.
	 * 
	 */
	public PlcMessageMap clearMensagens() {
		return mensagens = new PlcMessageMap();
	}
}
