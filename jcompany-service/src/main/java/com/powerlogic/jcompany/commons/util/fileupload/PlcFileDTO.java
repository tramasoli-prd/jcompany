/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.commons.util.fileupload;

import java.io.Serializable;

/**
 * 
 * PlcFileDTO para manipular o conteúdo dos arquivos anexados.
 * 
 * @category DTO
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public class PlcFileDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	

	private String nome;

	private String tipo;

	private Integer tamanho;

	private byte[] binaryContent;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public byte[] getBinaryContent() {
		return binaryContent;
	}

	public void setBinaryContent(byte[] binaryContent) {
		this.binaryContent = binaryContent;
	}

	
	
}
