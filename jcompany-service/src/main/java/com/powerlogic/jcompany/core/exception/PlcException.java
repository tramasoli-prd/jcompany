/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.exception;

import javax.ejb.ApplicationException;

import com.powerlogic.jcompany.core.messages.IPlcMessageKey;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.messages.PlcMessageType;

/**
 * 
 * Classe no padrão Wrapper
 * 
 * Classe para controle e tratamento de "Exceções Controladas" fornecido pelo Jaguar.
 * 
 * Mantém a exceção "raiz" para tratamento genérico e rastreio e manipulação.
 * 
 * @since 1.0.0
 * @author Powerlogic
 */
@ApplicationException(rollback = true)
public class PlcException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private PlcMessageMap messageMap;


	/** Contrutor padrão para exceptions.
	 *
	 */
	public PlcException() {
		super();
		getMessageMap().clear();
	}

	/**
	 * Permite o disparo de mensagens controladas durante a execução da requisição.
	 * 
	 * @param key - chave para adicionar a mensagem correspondente na pilha.
	 */
	public PlcException(IPlcMessageKey key) {
		addMessage(key);
	}

	/**
	 * Permite o disparo de mensagens controladas durante a execução da requisição.
	 * 
	 * @param key - chave para adicionar a mensagem correspondente na pilha.
	 * @param args - argumentos para troca de valores
	 */
	public PlcException(IPlcMessageKey key, String... args) {
		addMessage(key, args);
	}

	/**
	 * Permite o disparo de mensagens controladas durante a execução da requisição.
	 * 
	 * @param cause - Caura raiz da exceção.
	 * @param key - chave para adicionar a mensagem correspondente na pilha.
	 */
	public PlcException(Throwable cause, IPlcMessageKey key) {
		super(cause);
		addMessage(key);
	}

	/**
	 * Permite o disparo de mensagens controladas durante a execução da requisição.
	 * 
	 * @param cause - Caura raiz da exceção.
	 * @param key - chave para adicionar a mensagem correspondente na pilha.
	 * @param args - argumentos para troca de valores
	 */
	public PlcException(Throwable cause, IPlcMessageKey key, String... args) {
		super(cause);
		addMessage(key, args);
		addThrowable(cause);
	}

	/**
	 * Permite o disparo de mensagens controladas durante a execução da requisição.
	 * 
	 * @param messageMap - sobrescreve o mapa corrente (With Great Power Comes Great Responsibility)
	 */
	public PlcException(PlcMessageMap messageMap) {
		this.messageMap = messageMap;
	}

	/**
	 * Permite o disparo de mensagens controladas durante a execução da requisição.
	 * 
	 * @param cause - Caura raiz da exceção.
	 * @param messageMap - sobrescreve o mapa corrente 
	 */
	public PlcException(Throwable cause, PlcMessageMap messageMap) {
		super(cause);
		this.messageMap = messageMap;
	}

	/**
	 * Adiciona uma nova mensagem no mapa corrente.
	 * 
	 * @param key - chave para adicionar a mensagem correspondente na pilha.
	 * @param args - argumentos para troca de valores
	 */
	protected void addMessage(IPlcMessageKey key, String... args) {
		getMessageMap().addMessage(key, PlcMessageType.ERROR, args);
	}

	/**
	 * Converte o throwable e extrai a mensagem para adicionar no mapa.
	 * 
	 * @param throwable lancador
	 */
	protected void addThrowable(Throwable throwable) {
		if (throwable != this && throwable instanceof PlcException) {
			getMessageMap().addAll(((PlcException) throwable).getMessageMap());
		}
	}

	/**
	 * Recupera o mapa de mensagens manipulada durante a requisição.
	 * 
	 * @return mapa de mensagens.
	 */
	public PlcMessageMap getMessageMap() {
		if (messageMap == null) {
			messageMap = new PlcMessageMap();
		}
		return messageMap;
	}
}
