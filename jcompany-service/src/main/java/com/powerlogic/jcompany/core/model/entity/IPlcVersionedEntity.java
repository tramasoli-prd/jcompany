package com.powerlogic.jcompany.core.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;

public interface IPlcVersionedEntity<PK extends Serializable> extends IPlcEntityModel<PK> {

	Integer getVersao();
	void setVersao(Integer versao) ;
	
	public Date getDataCriacao();
	public void setDataCriacao(Date dataCriacao);

	public String getUsuarioAtualizacao();
	public void setUsuarioAtualizacao(String usuarioAtualizacao) ;
	
	public Date getDataAtualizacao();
	public void setDataAtualizacao(Date dataAtualizacao);

	public PlcSituacao getSituacao();
	public void setSituacao(PlcSituacao situacao);
}