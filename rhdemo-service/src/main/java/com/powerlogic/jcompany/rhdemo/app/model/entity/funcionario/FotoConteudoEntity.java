/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2010-2014.

		Please read licensing information in your installation directory.Contact Powerlogic for more 
		information or contribute with this project: suporte@powerlogic.com.br - www.powerlogic.com.br																								
 */
package com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.entity.PlcVersionedEntity;

/**
 * Arquivo de conteúdos de fotos
 */
@SuppressWarnings("serial")
@Entity
@Table(name="FOTO_CONTEUDO")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FotoConteudoEntity extends PlcVersionedEntity<Long> implements IPlcLogicalExclusion {

	@Id
	@Column(name = "PK_FOTO_CONTEUDO", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "CONTEUDO_BINARIO", length = Integer.MAX_VALUE)
	protected byte[] binaryContent;

	@JoinColumn(name = "PK_FOTO_CONTEUDO")
	@OneToOne
	@MapsId
	private FotoEntity foto;

	@Enumerated(EnumType.STRING)
	@Column(name = "IN_SITUACAO_REGISTRO", nullable = false, length = 1)
	private PlcSituacao situacao;
	
	public PlcSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(PlcSituacao situacao) {
		this.situacao = situacao;
	}
	
	public FotoEntity getFoto() {
		return foto;
	}

	public void setFoto(FotoEntity foto) {
		this.foto = foto;
	}

	public byte[] getBinaryContent() {
		return binaryContent;
	}

	public void setBinaryContent( byte[] content)    {
		this.binaryContent = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
