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

import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "FUNDAMENTO", schema = "CRONOS")
@SequenceGenerator(name = "FUNDAMENTO_ID_GENERATOR", sequenceName = "SE_FUNDAMENTO", schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FundamentoEntity extends VersionedEntity<Long>
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUNDAMENTO_ID_GENERATOR")
   @Column(name = "TJID_FUNDAMENTO", unique = true, nullable = false, precision = 8)
   private Long id;

   @Column(name = "NM_FUNDAMENTO", length = 60)
   private String nome;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }
}
