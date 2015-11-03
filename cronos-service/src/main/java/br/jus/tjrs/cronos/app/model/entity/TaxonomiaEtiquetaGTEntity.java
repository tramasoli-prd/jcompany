package br.jus.tjrs.cronos.app.model.entity;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TAXONOMIA_ETIQUETA_GT", schema = "CRONOS")
@SequenceGenerator(name = "TAXONOMIA_ETIQUETA_GT_GENERATOR", sequenceName = "SEQ_TAXONOMIA_ETIQUETA_GT", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxonomiaEtiquetaGTEntity extends VersionedEntity<Long> implements LogicalExclusion,
         Comparable<TaxonomiaEtiquetaGTEntity>
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAXONOMIA_ETIQUETA_GT_GENERATOR")
   @Column(name = "PK_TAXONOMIA_ETIQUETA_GT_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "ID_CATEGORIA", updatable = false, insertable = false)
   @XmlTransient
   private CategoriaItemEntity categoriaItem;

   @Column(name = "ID_CATEGORIA")
   private Long idCategoria;

   @ManyToOne
   @JoinColumn(name = "ID_ETIQUETA", updatable = false, insertable = false)
   private EtiquetaGTEntity etiquetaGT;

   @Column(name = "ID_ETIQUETA")
   private Long idEtiquetaGT;

   @Column(name = "ORDEM")
   private Integer ordem;

   @Column(name = "ICONE_PADRAO")
   private String icone;

   @Column(name = "ICONE_PADRAO_COR")
   private String cor;

   @OneToOne
   @JoinColumn(name = "ID_ICONE", updatable = false, insertable = false)
   private IconesPersonalEntity iconePersonal;

   @Column(name = "ID_ICONE")
   private Long idIcone;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public CategoriaItemEntity getCategoriaItem()
   {
      return categoriaItem;
   }

   public void setCategoriaItem(CategoriaItemEntity categoriaItem)
   {
      this.categoriaItem = categoriaItem;
   }

   public EtiquetaGTEntity getEtiquetaGT()
   {
      return etiquetaGT;
   }

   public void setEtiquetaGT(EtiquetaGTEntity etiquetaGT)
   {
      this.etiquetaGT = etiquetaGT;
   }

   public Integer getOrdem()
   {
      return ordem;
   }

   public void setOrdem(Integer ordem)
   {
      this.ordem = ordem;
   }

   public Long getIdCategoria()
   {
      return idCategoria;
   }

   public void setIdCategoria(Long idCategoria)
   {
      this.idCategoria = idCategoria;
   }

   public Long getIdEtiquetaGT()
   {
      return idEtiquetaGT;
   }

   public void setIdEtiquetaGT(Long idEtiquetaGT)
   {
      this.idEtiquetaGT = idEtiquetaGT;
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

   public TaxonomiaEtiquetaGTEntity clone(CategoriaItemEntity categoria, EtiquetaGTEntity etiqueta)
   {
      TaxonomiaEtiquetaGTEntity entity = new TaxonomiaEtiquetaGTEntity();
      entity.setEtiquetaGT(this.etiquetaGT);
      entity.setOrdem(this.ordem);
      entity.setIdCategoria(categoria.getId());
      entity.setIdEtiquetaGT(etiqueta.getId());
      entity.setIcone(this.icone);
      // default
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }

   public static final Comparator<TaxonomiaEtiquetaGTEntity> ORDEM_COMPARATOR = new Comparator<TaxonomiaEtiquetaGTEntity>()
   {
      @Override
      public int compare(TaxonomiaEtiquetaGTEntity o1, TaxonomiaEtiquetaGTEntity o2)
      {
         if (o1.getOrdem() != null || o2.getOrdem() != null)
         {
            return o1.getOrdem().compareTo(o2.getOrdem());
         }
         if (o1.getOrdem() != null)
         {
            return 1;
         }
         if (o2.getOrdem() != null)
         {
            return -1;
         }
         return 0;
      }
   };

   @Override
   public int compareTo(TaxonomiaEtiquetaGTEntity o)
   {
      if (this.getOrdem() != null || o.getOrdem() != null)
      {
         return this.getOrdem().compareTo(o.getOrdem());
      }
      if (this.getOrdem() != null)
      {
         return 1;
      }
      if (o.getOrdem() != null)
      {
         return -1;
      }
      return 0;
   }
}
