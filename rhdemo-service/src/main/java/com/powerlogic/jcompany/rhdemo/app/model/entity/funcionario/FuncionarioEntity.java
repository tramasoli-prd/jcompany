package com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario;

import com.powerlogic.jcompany.commons.validation.Cpf;
import com.powerlogic.jcompany.commons.validation.Email;
import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.entity.PlcVersionedEntity;
import com.powerlogic.jcompany.rhdemo.app.model.domain.EstadoCivil;
import com.powerlogic.jcompany.rhdemo.app.model.domain.Sexo;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Entidade Funcionario
 */
@Entity
@Table(name = "FUNCIONARIO")
@SequenceGenerator(name = "SE_FUNCIONARIO", sequenceName = "SE_FUNCIONARIO")
@Access(AccessType.FIELD)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)


@NamedQueries({
	@NamedQuery(name = "FuncionarioEntity.find", 
			query = "SELECT f from FuncionarioEntity f where f.situacao = :situacao order by f.id asc")
})


public class FuncionarioEntity extends PlcVersionedEntity<Long> implements IPlcLogicalExclusion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 867075439636862292L;

	
	@Id 
 	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SE_FUNCIONARIO")
	@Column(nullable=false,length=5)
	private Long id;
	
	@NotNull 
	@Size(min=5, max = 40)
	@Column
	private String nome;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	
	@NotNull 
	@Cpf
	private String cpf;
	
	@Size(max = 60) 
	@Email
	private String email;
	
	@Enumerated(EnumType.STRING)
	@NotNull 
	@Column(length=1)
	private EstadoCivil estadoCivil;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Sexo sexo;
	
	@Column(length=1)
	private Boolean temCursoSuperior = false;
	
	@ManyToOne (targetEntity = DepartamentoEntity.class)
	@JoinColumn
	private DepartamentoEntity departamento;
	
	@Size(max = 255) 
	@Column
	private String observacao;
	
//    @OneToMany (targetEntity = CurriculoEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL)
//    @OrderBy("id")
//    @Valid
//    private List<CurriculoEntity> curriculo;

    @OneToOne (targetEntity = FotoEntity.class, cascade=CascadeType.ALL, orphanRemoval=true)    
    @JoinColumn(name="ID_FOTO")
    private FotoEntity foto;

	
	@OneToMany (targetEntity = HistoricoProfissionalEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="funcionario", orphanRemoval=true)
	@Valid
	private List<HistoricoProfissionalEntity> historicoProfissional;
	
	@OneToMany (targetEntity = DependenteEntity.class, fetch = FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="funcionario", orphanRemoval=true)
	@Valid
	private List<DependenteEntity> dependente;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "IN_SITUACAO_REGISTRO", nullable = false, length = 1)
	private PlcSituacao situacao;
	
	@Transient
	private Date dataNascimentoFiltroInicio;
	@Transient
	private Date dataNascimentoFiltroFim;
	
	
	public PlcSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(PlcSituacao situacao) {
		this.situacao = situacao;
	}
	
	/** atributo para repassar o nome do arquivo vindo do upload
	 */
	@Transient
	private String fileName; 
	
	@AssertTrue(message="{funcionario.valida.maioridade}")
	public boolean isMaiorDeIdade() {
		//TODO: FAZER O CALCULO...
		return true;
	}	

	/**
	 * 
	 */
	public FuncionarioEntity() {
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getNome();
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
	 * @return the dataNascimento
	 */
	public Date getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the estadoCivil
	 */
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return the sexo
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the temCursoSuperior
	 */
	public Boolean getTemCursoSuperior() {
		return temCursoSuperior;
	}

	/**
	 * @param temCursoSuperior the temCursoSuperior to set
	 */
	public void setTemCursoSuperior(Boolean temCursoSuperior) {
		this.temCursoSuperior = temCursoSuperior;
	}

	/**
	 * @return the departamento
	 */
	public DepartamentoEntity getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(DepartamentoEntity departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

//	/**
//	 * @return the curriculo
//	 */
//	public List<CurriculoEntity> getCurriculo() {
//		return curriculo;
//	}
//
//	/**
//	 * @param curriculo the curriculo to set
//	 */
//	public void setCurriculo(List<CurriculoEntity> curriculo) {
//		this.curriculo = curriculo;
//	}

	/**
	 * @return the foto
	 */
	public FotoEntity getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(FotoEntity foto) {
		this.foto = foto;
	}

	/**
	 * @return the historicoProfissional
	 */
	public List<HistoricoProfissionalEntity> getHistoricoProfissional() {
		return historicoProfissional;
	}

	/**
	 * @param historicoProfissional the historicoProfissional to set
	 */
	public void setHistoricoProfissional(
			List<HistoricoProfissionalEntity> historicoProfissional) {
		this.historicoProfissional = historicoProfissional;
	}

	/**
	 * @return the dependente
	 */
	public List<DependenteEntity> getDependente() {
		return dependente;
	}

	/**
	 * @param dependente the dependente to set
	 */
	public void setDependente(List<DependenteEntity> dependente) {
		this.dependente = dependente;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the dataNascimentoFiltroInicio
	 */
	public Date getDataNascimentoFiltroInicio() {
		return dataNascimentoFiltroInicio;
	}

	/**
	 * @param dataNascimentoFiltroInicio the dataNascimentoFiltroInicio to set
	 */
	public void setDataNascimentoFiltroInicio(Date dataNascimentoFiltroInicio) {
		this.dataNascimentoFiltroInicio = dataNascimentoFiltroInicio;
	}

	/**
	 * @return the dataNascimentoFiltroFim
	 */
	public Date getDataNascimentoFiltroFim() {
		return dataNascimentoFiltroFim;
	}

	/**
	 * @param dataNascimentoFiltroFim the dataNascimentoFiltroFim to set
	 */
	public void setDataNascimentoFiltroFim(Date dataNascimentoFiltroFim) {
		this.dataNascimentoFiltroFim = dataNascimentoFiltroFim;
	}

	
	
	

}
