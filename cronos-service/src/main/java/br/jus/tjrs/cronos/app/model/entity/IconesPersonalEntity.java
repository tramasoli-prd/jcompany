package br.jus.tjrs.cronos.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "ICONES_PERSONAL", schema = "CRONOS")
@SequenceGenerator(name = "ICONES_PERSONAL_GENERATOR", sequenceName = "SEQ_ICONES_PERSONAL", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IconesPersonalEntity extends VersionedEntity<Long> implements LogicalExclusion
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ICONES_PERSONAL_GENERATOR")
   @Column(name = "PK_ICONE_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Lob
   @Column(name = "IMAGEM")
   private String imagem;

   @Column(name = "TIPO")
   private String tipo;

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
    * @return the imagem
    */
   public String getImagem()
   {
      return imagem;
   }

   /**
    * @param imagem the imagem to set
    */
   public void setImagem(String imagem)
   {
      this.imagem = imagem;
   }

   /**
    * @return the tipo
    */
   public String getTipo()
   {
      return tipo;
   }

   /**
    * @param tipo the tipo to set
    */
   public void setTipo(String tipo)
   {
      this.tipo = tipo;
   }
}
