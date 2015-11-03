package br.jus.tjrs.cronos.app.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TAXONOMIA_SENTENCA", schema = "CRONOS")
@SequenceGenerator(name = "TAXONOMIA_SENTENCA_GENERATOR", sequenceName = "SEQ_TAXONOMIA_CATEGORIA", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxonomiaSentencaEntity extends VersionedEntity<Long> implements LogicalExclusion,
         Comparable<TaxonomiaSentencaEntity>
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAXONOMIA_SENTENCA_GENERATOR")
   @Column(name = "PK_TAXONOMIA_CAT_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "ID_CATEGORIA", updatable = false, insertable = false)
   private CategoriaItemEntity categoriaItem;

   @Column(name = "ID_CATEGORIA")
   private Long idCategoria;

   @ManyToOne
   @JoinColumn(name = "ID_SENTENCA", updatable = false, insertable = false)
   @XmlTransient
   private SentencaEntity sentenca;

   @Column(name = "ID_SENTENCA")
   private Long idSentenca;

   @Column(name = "ORDEM")
   private Integer ordem;

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

   public Long getIdCategoria()
   {
      return idCategoria;
   }

   public void setIdCategoria(Long idCategoria)
   {
      this.idCategoria = idCategoria;
   }

   public Integer getOrdem()
   {
      return ordem;
   }

   public void setOrdem(Integer ordem)
   {
      this.ordem = ordem;
   }

   /**
    * @return the sentenca
    */
   public SentencaEntity getSentenca()
   {
      return sentenca;
   }

   /**
    * @param sentenca the sentenca to set
    */
   public void setSentenca(SentencaEntity sentenca)
   {
      this.sentenca = sentenca;
   }

   /**
    * @return the idSentenca
    */
   public Long getIdSentenca()
   {
      return idSentenca;
   }

   /**
    * @param idSentenca the idSentenca to set
    */
   public void setIdSentenca(Long idSentenca)
   {
      this.idSentenca = idSentenca;
   }

   public TaxonomiaSentencaEntity clone(SentencaEntity sentenca, CategoriaItemEntity categoria)
   {
      TaxonomiaSentencaEntity entity = new TaxonomiaSentencaEntity();
      entity.setSentenca(this.sentenca);
      entity.setCategoriaItem(categoria);
      entity.setOrdem(this.ordem);
      entity.setIdSentenca(sentenca.getId());
      entity.setIdCategoria(categoria.getId());
      // default
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }

   @Override
   public int compareTo(TaxonomiaSentencaEntity o)
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
