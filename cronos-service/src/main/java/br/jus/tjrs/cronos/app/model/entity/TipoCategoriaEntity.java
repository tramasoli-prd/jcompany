package br.jus.tjrs.cronos.app.model.entity;

import java.util.Date;

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

import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "TIPO_CATEGORIA", schema = "CRONOS")
@SequenceGenerator(name = "TIPO_CATEGORIA_GENERATOR", sequenceName = "SEQ_TIPO_CATEGORIA", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoCategoriaEntity extends VersionedEntity<Long> implements LogicalExclusion, Cloneable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_CATEGORIA_GENERATOR")
   @Column(name = "PK_TIPO_CATEGORIA_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "NOME", length = 150)
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

   public TipoCategoriaEntity clone()
   {
      TipoCategoriaEntity entity = new TipoCategoriaEntity();
      entity.setNome(this.nome);
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }

}
