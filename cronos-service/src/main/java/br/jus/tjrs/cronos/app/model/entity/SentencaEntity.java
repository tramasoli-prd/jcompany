package br.jus.tjrs.cronos.app.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "SENTENCA_GT", schema = "CRONOS")
@SequenceGenerator(name = "SENTENCA_GENERATOR", sequenceName = "SEQ_SENTENCA", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SentencaEntity extends VersionedEntity<Long> implements LogicalExclusion
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SENTENCA_GENERATOR")
   @Column(name = "PK_SENTENCA_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @OneToOne
   @JoinColumn(name = "ID_GRUPO_TRABALHO", updatable = false, insertable = false)
   @XmlTransient
   private GrupoTrabalhoEntity grupoTrabalho;

   @Column(name = "ID_GRUPO_TRABALHO")
   private Long idGrupoTrabalho;

   @ManyToOne
   @JoinColumn(name = "PAI", updatable = false, insertable = false)
   @XmlTransient
   private SentencaEntity pai;

   @Column(name = "PAI")
   private Long idPai;

   @Enumerated(EnumType.STRING)
   @Column(name = "FINALIZADA")
   private SimNao finalizada;

   @Column(name = "ICONE_PADRAO", length = 30)
   private String icone;

   @Column(name = "ICONE_PADRAO_COR")
   private String cor;

   @Column(name = "ID_ICONE")
   private Long idIcone;

   @Lob
   @Column(name = "SENTENCA_PRONTA")
   private String sentencaPronta;

   @Column(name = "NR_PROCESSO")
   private String numeroProcesso;

   @OneToMany(mappedBy = "sentenca")
   private List<TaxonomiaSentencaEntity> modeloSentenca;

   @OneToMany(mappedBy = "sentenca")
   private List<TaxonomiaSentencaEntity> topicos;

   @Column(name = "ORDEM")
   private Integer ordem;

   /**
    * @return the id
    */
   public Long getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(Long id)
   {
      this.id = id;
   }

   /**
    * @return the grupoTrabalho
    */
   public GrupoTrabalhoEntity getGrupoTrabalho()
   {
      return grupoTrabalho;
   }

   /**
    * @param grupoTrabalho the grupoTrabalho to set
    */
   public void setGrupoTrabalho(GrupoTrabalhoEntity grupoTrabalho)
   {
      this.grupoTrabalho = grupoTrabalho;
   }

   /**
    * @return the idGrupoTrabalho
    */
   public Long getIdGrupoTrabalho()
   {
      return idGrupoTrabalho;
   }

   /**
    * @param idGrupoTrabalho the idGrupoTrabalho to set
    */
   public void setIdGrupoTrabalho(Long idGrupoTrabalho)
   {
      this.idGrupoTrabalho = idGrupoTrabalho;
   }

   /**
    * @return the pai
    */
   public SentencaEntity getPai()
   {
      return pai;
   }

   /**
    * @param pai the pai to set
    */
   public void setPai(SentencaEntity pai)
   {
      this.pai = pai;
   }

   /**
    * @return the idPai
    */
   public Long getIdPai()
   {
      return idPai;
   }

   /**
    * @param idPai the idPai to set
    */
   public void setIdPai(Long idPai)
   {
      this.idPai = idPai;
   }

   /**
    * @return the finalizada
    */
   public SimNao getFinalizada()
   {
      return finalizada;
   }

   /**
    * @param finalizada the finalizada to set
    */
   public void setFinalizada(SimNao finalizada)
   {
      this.finalizada = finalizada;
   }

   /**
    * @return the icone
    */
   public String getIcone()
   {
      return icone;
   }

   /**
    * @param icone the icone to set
    */
   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   /**
    * @return the cor
    */
   public String getCor()
   {
      return cor;
   }

   /**
    * @param cor the cor to set
    */
   public void setCor(String cor)
   {
      this.cor = cor;
   }

   /**
    * @return the idIcone
    */
   public Long getIdIcone()
   {
      return idIcone;
   }

   /**
    * @param idIcone the idIcone to set
    */
   public void setIdIcone(Long idIcone)
   {
      this.idIcone = idIcone;
   }

   /**
    * @return the sentencaPronta
    */
   public String getSentencaPronta()
   {
      return sentencaPronta;
   }

   /**
    * @param sentencaPronta the sentencaPronta to set
    */
   public void setSentencaPronta(String sentencaPronta)
   {
      this.sentencaPronta = sentencaPronta;
   }

   /**
    * @return the modeloSentenca
    */
   public List<TaxonomiaSentencaEntity> getModeloSentenca()
   {
      return modeloSentenca;
   }

   /**
    * @param modeloSentenca the modeloSentenca to set
    */
   public void setModeloSentenca(List<TaxonomiaSentencaEntity> modeloSentenca)
   {
      this.modeloSentenca = modeloSentenca;
   }

   /**
    * @return the topicos
    */
   public List<TaxonomiaSentencaEntity> getTopicos()
   {
      return topicos;
   }

   /**
    * @param topicos the topicos to set
    */
   public void setTopicos(List<TaxonomiaSentencaEntity> topicos)
   {
      this.topicos = topicos;
   }

   /**
    * @return the numeroProcesso
    */
   public String getNumeroProcesso()
   {
      return numeroProcesso;
   }

   /**
    * @param numeroProcesso the numeroProcesso to set
    */
   public void setNumeroProcesso(String numeroProcesso)
   {
      this.numeroProcesso = numeroProcesso;
   }

   /**
    * @return the ordem
    */
   public Integer getOrdem()
   {
      return ordem;
   }

   /**
    * @param ordem the ordem to set
    */
   public void setOrdem(Integer ordem)
   {
      this.ordem = ordem;
   }

}
