package br.jus.tjrs.cronos.app.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.app.model.domain.Alinhamento;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "TIPO_ELEMENTO", schema = "CRONOS")
@SequenceGenerator(name = "TIPO_ELEMENTO_GENERATOR", sequenceName = "SEQ_TIPO_ELEMENTO", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoElementoEntity extends VersionedEntity<Long> implements LogicalExclusion, Cloneable
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_ELEMENTO_GENERATOR")
   @Column(name = "PK_TIPO_ELEMENTO_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "NOME_ITEM", nullable = false)
   private String nomeItem;

   @Enumerated(EnumType.STRING)
   @Column(name = "ALINHAMENTO", nullable = false)
   private Alinhamento alinhamento;

   @Column(name = "RECUO", nullable = false)
   private Integer recuo;

   @Column(name = "ICONE_PADRAO")
   private String icone;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getNomeItem()
   {
      return nomeItem;
   }

   public void setNomeItem(String nomeItem)
   {
      this.nomeItem = nomeItem;
   }

   public Alinhamento getAlinhamento()
   {
      return alinhamento;
   }

   public void setAlinhamento(Alinhamento alinhamento)
   {
      this.alinhamento = alinhamento;
   }

   public Integer getRecuo()
   {
      return recuo;
   }

   public void setRecuo(Integer recuo)
   {
      this.recuo = recuo;
   }

   public String getIcone()
   {
      return icone;
   }

   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   public TipoElementoEntity clone()
   {
      TipoElementoEntity entity = new TipoElementoEntity();
      entity.setAlinhamento(this.alinhamento);
      entity.setNomeItem(this.nomeItem);
      entity.setRecuo(this.recuo);
      entity.setIcone(this.icone);

      // default
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }

}
