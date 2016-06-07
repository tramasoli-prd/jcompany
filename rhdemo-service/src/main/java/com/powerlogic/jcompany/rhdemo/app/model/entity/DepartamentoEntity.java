package com.powerlogic.jcompany.rhdemo.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.entity.PlcVersionedEntity;


/** Entidade Departamento 
 * 
 */
@Entity
@Table(name = "DEPARTAMENTO")
@SequenceGenerator(name = "DEPARTAMENTO_ID_GENERATOR", sequenceName = "SE_DEPARTAMENTO", allocationSize = 1)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = "DepartamentoEntity.find", query = "SELECT u FROM DepartamentoEntity u WHERE u.descricao = :nome order by u.descricao"),
	@NamedQuery(name = "DepartamentoEntity.checkConstraintsBeforeRemove.departamentoPai", query = "SELECT count(o.id) FROM DepartamentoEntity o WHERE o.departamentoPai.id = :id"),
	@NamedQuery(name = "DepartamentoEntity.checkConstraintsBeforeSave.nomeUnico", query = "SELECT count(o.id) FROM DepartamentoEntity o WHERE o.descricao like :descricao and o.id <> :id")
})
public class DepartamentoEntity extends PlcVersionedEntity<Long> implements IPlcLogicalExclusion {

	private static final long serialVersionUID = 1L;

	/**
	 *  Construtor Default
	 */
	public DepartamentoEntity() {}
	
	/** Construtor
	 * 
	 * @param id
	 */
	public DepartamentoEntity(Long id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @param id
	 * @param descricao
	 */
	public DepartamentoEntity(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	/**
	 * 
	 * @param id
	 * @param descricao
	 */
	public DepartamentoEntity(Long id, String descricao, String descricaoDepartamentoPai) {
		this.id = id;
		this.descricao = descricao;
		this.departamentoPai = new DepartamentoEntity();
		this.departamentoPai.setDescricao(descricaoDepartamentoPai);
	}
	
	/** atributo chave primaria
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_ID_GENERATOR")
	@Column(name = "PK_DEPARTAMENTO", unique = true, nullable = false, precision = 10)
	private Long id;

	/** atributo descricao do departamento
	 */
	@NotNull
	@Size(max=100)
	@Column(name = "DESCRICAO", length = 100)
	private String descricao;

	/** atributo departamento pai
	 */
	@ManyToOne (targetEntity = DepartamentoEntity.class)
	@JoinColumn (name = "ID_DEPARTAMENTO_PAI")
	private DepartamentoEntity departamentoPai;

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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the departamentoPai
	 */
	public DepartamentoEntity getDepartamentoPai() {
		return departamentoPai;
	}

	/**
	 * @param departamentoPai the departamentoPai to set
	 */
	public void setDepartamentoPai(DepartamentoEntity departamentoPai) {
		this.departamentoPai = departamentoPai;
	}


	private transient boolean pesquisaPaiIsNull;
	private transient boolean pesquisaPaiIsNotNull;
	private transient boolean pesquisaDescricaoIsNull;
	private transient boolean pesquisaDescricaoIsNotNull;

	public boolean isPesquisaPaiIsNull() {
		return pesquisaPaiIsNull;
	}

	public void setPesquisaPaiIsNull(boolean pesquisaPaiIsNull) {
		this.pesquisaPaiIsNull = pesquisaPaiIsNull;
	}

	public boolean isPesquisaPaiIsNotNull() {
		return pesquisaPaiIsNotNull;
	}

	public void setPesquisaPaiIsNotNull(boolean pesquisaPaiIsNotNull) {
		this.pesquisaPaiIsNotNull = pesquisaPaiIsNotNull;
	}

	public boolean isPesquisaDescricaoIsNull() {
		return pesquisaDescricaoIsNull;
	}

	public void setPesquisaDescricaoIsNull(boolean pesquisaDescricaoIsNull) {
		this.pesquisaDescricaoIsNull = pesquisaDescricaoIsNull;
	}

	public boolean isPesquisaDescricaoIsNotNull() {
		return pesquisaDescricaoIsNotNull;
	}

	public void setPesquisaDescricaoIsNotNull(boolean pesquisaDescricaoIsNotNull) {
		this.pesquisaDescricaoIsNotNull = pesquisaDescricaoIsNotNull;
	}

	
}
