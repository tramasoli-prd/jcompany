/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.powerlogic.jcompany.core.listener.PlcVersionedListener;
import com.powerlogic.jcompany.core.model.domain.PlcSituacao;

@MappedSuperclass
@EntityListeners(PlcVersionedListener.class)
public abstract class PlcVersionedEntity<PK extends Serializable> implements IPlcVersionedEntity<PK> {
	
	private static final long serialVersionUID = 1L;

	@Version
	@NotNull
	@Column(name = "VERSAO_REGISTRO")
	@Digits(integer = 8, fraction = 0)
	private Integer versao = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CRIACAO", updatable = false)
	private Date dataCriacao;

	@Column(name = "NM_ULT_ALTERACAO", length = 150)
	private String usuarioAtualizacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_ULT_ALTERACAO")
	private Date dataAtualizacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "IN_SITUACAO_REGISTRO", nullable = false, length = 1)
	private PlcSituacao situacao;

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getUsuarioAtualizacao() {
		return usuarioAtualizacao;
	}

	public void setUsuarioAtualizacao(String usuarioAtualizacao) {
		this.usuarioAtualizacao = usuarioAtualizacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public PlcSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(PlcSituacao situacao) {
		this.situacao = situacao;
	}
	
	@Override
	public boolean isIdSet() {
		if(getId()!=null)
			return true;
		return false;
	}
}
