package com.powerlogic.jcompany.model.test;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.entity.PlcVersionedEntity;

@Entity
@Table(name="TEST_USUARIO")
public class UserEntity extends PlcVersionedEntity<Long> implements IPlcLogicalExclusion{

	
	@SequenceGenerator(name="SE_USUARIO", sequenceName="TEST_SE_USUARIO")	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_USUARIO")
	@Column (name = "ID_USUARIO", nullable=false, length=5) 
    private Long id;
	
	@Column (name = "NOME", nullable=false, length=50)
    private String name;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "IN_SITUACAO_REGISTRO", nullable = false, length = 1)
	private PlcSituacao situacao;
	
	public PlcSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(PlcSituacao situacao) {
		this.situacao = situacao;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

}