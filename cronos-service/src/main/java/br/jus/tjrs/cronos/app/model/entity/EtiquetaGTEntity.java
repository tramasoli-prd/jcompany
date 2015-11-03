package br.jus.tjrs.cronos.app.model.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "ETIQUETA_GT", schema = "CRONOS")
@SequenceGenerator(name = "ETIQUETA_GT_GENERATOR", sequenceName = "SEQ_ETIQUETA_GT", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EtiquetaGTEntity extends VersionedEntity<Long> implements LogicalExclusion, Cloneable
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ETIQUETA_GT_GENERATOR")
   @Column(name = "PK_ETIQUETA_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "ETIQUETA", length = 100, nullable = false)
   private String etiqueta;

   @Column(name = "ICONE_PADRAO", length = 30)
   private String icone;

   @Column(name = "ID_ICONE")
   private Long idIcone;

   @OneToMany(mappedBy = "etiquetaGT")
   @XmlTransient
   private List<TaxonomiaEtiquetaGTEntity> taxonomias;

   @Column(name = "ICONE_PADRAO_COR")
   private String cor;

   @OneToOne
   @JoinColumn(name = "ID_ICONE", updatable = false, insertable = false)
   private IconesPersonalEntity iconePersonal;

   @OneToOne
   @JoinColumn(name = "ID_GRUPO_TRABALHO", updatable = false, insertable = false)
   @XmlTransient
   private GrupoTrabalhoEntity grupoTrabalho;

   @Column(name = "ID_GRUPO_TRABALHO")
   private Long idGrupoTrabalho;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getEtiqueta()
   {
      return etiqueta;
   }

   public void setEtiqueta(String etiqueta)
   {
      this.etiqueta = etiqueta;
   }

   public String getIcone()
   {
      return icone;
   }

   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   public List<TaxonomiaEtiquetaGTEntity> getTaxonomias()
   {
      return taxonomias;
   }

   public void setTaxonomias(List<TaxonomiaEtiquetaGTEntity> taxonomias)
   {
      this.taxonomias = taxonomias;
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
    * @return the iconePersonal
    */
   public IconesPersonalEntity getIconePersonal()
   {
      return iconePersonal;
   }

   /**
    * @param iconePersonal the iconePersonal to set
    */
   public void setIconePersonal(IconesPersonalEntity iconePersonal)
   {
      this.iconePersonal = iconePersonal;
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

   public EtiquetaGTEntity clone(Long idGrupoTrabalho)
   {
      EtiquetaGTEntity entity = new EtiquetaGTEntity();
      entity.setEtiqueta(this.etiqueta);
      entity.setIcone(this.icone);
      entity.setIdIcone(this.idIcone);
      entity.setIdGrupoTrabalho(idGrupoTrabalho);

      if (this.iconePersonal != null)
      {
         entity.setIconePersonal(this.iconePersonal);
         entity.setIdIcone(this.iconePersonal.getId());
      }

      // default
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }
}
