package br.jus.tjrs.cronos.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "TIPO_ALEGACAO", schema = "CRONOS")
@SequenceGenerator(name = "TIPO_ALEGACAO_GENERATOR", sequenceName = "SEQ_TIPO_ALEGACAO", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoAlegacaoEntity extends VersionedEntity<Long> implements LogicalExclusion, Cloneable
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_ALEGACAO_GENERATOR")
   @Column(name = "PK_ALEGA_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "QUEM_ALEGA", nullable = false)
   private String quemAlega;

   @Column(name = "LABEL", nullable = false)
   private String label;

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
    * @return the quemAlega
    */
   public String getQuemAlega()
   {
      return quemAlega;
   }

   /**
    * @param quemAlega the quemAlega to set
    */
   public void setQuemAlega(String quemAlega)
   {
      this.quemAlega = quemAlega;
   }

   /**
    * @return the label
    */
   public String getLabel()
   {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label)
   {
      this.label = label;
   }

}
