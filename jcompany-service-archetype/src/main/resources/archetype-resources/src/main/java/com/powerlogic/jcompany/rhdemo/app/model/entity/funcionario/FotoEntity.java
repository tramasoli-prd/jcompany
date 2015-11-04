#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2010-2014.
			    		    
		Please read licensing information in your installation directory.Contact Powerlogic for more 
		information or contribute with this project: suporte@powerlogic.com.br - www.powerlogic.com.br																								
*/
package com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.powerlogic.jcompany.core.model.entity.PlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.entity.PlcVersionedEntity;

/**
 * Arquivo de Fotos
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FOTO")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SequenceGenerator(name = "FOTO_ID_GENERATOR", sequenceName = "SE_FOTO")
public class FotoEntity extends PlcVersionedEntity<Long>implements PlcLogicalExclusion {

	public FotoEntity() {
	}
	
	public FotoEntity(Long id) {
		this.id = id;
	}
	
	public FotoEntity(String nome) {
		this.nome = nome;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOTO_ID_GENERATOR")
	@Column(name = "PK_FOTO", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "TIPO")
	protected String tipo;

	@Column(name = "TAMANHO")
	protected Integer tamanho;

	@OneToOne(targetEntity = FotoConteudoEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, mappedBy="foto")
	@XmlTransient
	protected FotoConteudoEntity conteudo;

	protected String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public FotoConteudoEntity getConteudo() {
		return conteudo;
	}

	public void setConteudo(FotoConteudoEntity conteudo) {
		this.conteudo = conteudo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}