package br.jus.tjrs.cronos.app.model.entity;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "ELEMENTO_IGT", schema = "CRONOS")
@SequenceGenerator(name = "ELEMENTO_IGT_GENERATOR", sequenceName = "SEQ_ELEMENTO_IGT", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ElementoIGTEntity extends VersionedEntity<Long> implements LogicalExclusion, Comparable<ElementoIGTEntity>
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ELEMENTO_IGT_GENERATOR")
   @Column(name = "PK_ELEMENTO_IGT_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "TITULO", length = 100)
   private String titulo;

   @Lob
   @Column(name = "TEXTO")
   private String texto;

   @Column(name = "ORDEM")
   private Long ordem;

   @Column(name = "ICONE_PADRAO")
   private String icone;

   @ManyToOne
   @JoinColumn(name = "ID_CATEGORIA_ITEM", updatable = false, insertable = false)
   @XmlTransient
   private CategoriaItemEntity categoria;

   @Column(name = "ID_CATEGORIA_ITEM")
   private Long idCategoria;

   @OneToOne
   @JoinColumn(name = "ID_TIPO_ELEMENTO", updatable = false, insertable = false)
   private TipoElementoEntity tipoElemento;

   @Column(name = "ID_TIPO_ELEMENTO")
   private Long idTipoElemento;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getTitulo()
   {
      return titulo;
   }

   public void setTitulo(String titulo)
   {
      this.titulo = titulo;
   }

   public String getTexto()
   {
      return texto;
   }

   public void setTexto(String texto)
   {
      this.texto = texto;
   }

   public Long getOrdem()
   {
      return ordem;
   }

   public void setOrdem(Long ordem)
   {
      this.ordem = ordem;
   }

   public String getIcone()
   {
      return icone;
   }

   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   public CategoriaItemEntity getCategoria()
   {
      return categoria;
   }

   public void setCategoria(CategoriaItemEntity categoria)
   {
      this.categoria = categoria;
   }

   public TipoElementoEntity getTipoElemento()
   {
      return tipoElemento;
   }

   public void setTipoElemento(TipoElementoEntity tipoElemento)
   {
      this.tipoElemento = tipoElemento;
   }

   public Long getIdCategoria()
   {
      return idCategoria;
   }

   public void setIdCategoria(Long idCategoria)
   {
      this.idCategoria = idCategoria;
   }

   public Long getIdTipoElemento()
   {
      return idTipoElemento;
   }

   public void setIdTipoElemento(Long idTipoElemento)
   {
      this.idTipoElemento = idTipoElemento;
   }

   public ElementoIGTEntity clone(CategoriaItemEntity categoriaEntity)
   {
      ElementoIGTEntity entity = new ElementoIGTEntity();
      entity.setIdCategoria(categoriaEntity.getId());
      entity.setTipoElemento(this.tipoElemento);
      entity.setIdTipoElemento(this.tipoElemento.getId());
      entity.setIcone(this.icone);
      entity.setOrdem(this.ordem);
      entity.setTitulo(this.titulo);
      entity.setTexto(this.texto);
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }

   public static final Comparator<ElementoIGTEntity> ORDEM_COMPARATOR = new Comparator<ElementoIGTEntity>()
   {
      @Override
      public int compare(ElementoIGTEntity o1, ElementoIGTEntity o2)
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
   public int compareTo(ElementoIGTEntity o)
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
