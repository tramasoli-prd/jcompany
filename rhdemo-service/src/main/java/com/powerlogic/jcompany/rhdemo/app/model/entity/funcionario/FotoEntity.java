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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.entity.PlcVersionedEntity;

/**
 * Entidade Foto
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "FOTO")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@SequenceGenerator(name = "FOTO_ID_GENERATOR", sequenceName = "SE_FOTO")
public class FotoEntity extends PlcVersionedEntity<Long>implements IPlcLogicalExclusion {
	
	/** atributo PK
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOTO_ID_GENERATOR")
	@Column(name = "PK_FOTO", unique = true, nullable = false, precision = 10)
	private Long id;

	/** atributo nome foto
	 */
	@Column(name = "NOME")
	private String nome;

	/** atributo tipo de arquivo
	 */
	@Column(name = "TIPO")
	private String tipo;

	/** atributo tamanho arquivo
	 */
	@Column(name = "TAMANHO")
	private Integer tamanho;

	/** atributo conteudo da arquivo
	 */
	@OneToOne(targetEntity = FotoConteudoEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false, mappedBy="foto", orphanRemoval=true)
	@XmlTransient
	private FotoConteudoEntity conteudo;

	@Enumerated(EnumType.STRING)
	@Column(name = "IN_SITUACAO_REGISTRO", nullable = false, length = 1)
	private PlcSituacao situacao;
	
	public PlcSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(PlcSituacao situacao) {
		this.situacao = situacao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the tamanho
	 */
	public Integer getTamanho() {
		return tamanho;
	}

	/**
	 * @param tamanho the tamanho to set
	 */
	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	/**
	 * @return the conteudo
	 */
	public FotoConteudoEntity getConteudo() {
		return conteudo;
	}

	/**
	 * @param conteudo the conteudo to set
	 */
	public void setConteudo(FotoConteudoEntity conteudo) {
		this.conteudo = conteudo;
	}

}