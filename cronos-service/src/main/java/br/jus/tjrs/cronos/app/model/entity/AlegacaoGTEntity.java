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
@Table(name = "ALEGACAO_GT", schema = "CRONOS")
@SequenceGenerator(name = "ALEGACAO_GT_GENERATOR", sequenceName = "SEQ_ALEGACAO_GT", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlegacaoGTEntity extends VersionedEntity<Long> implements LogicalExclusion, Cloneable
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ALEGACAO_GT_GENERATOR")
   @Column(name = "PK_ALEGACAO_GT_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "ID_ALEGACAO", nullable = false)
   private Long idTipoAlegacao;

   @JoinColumn(name = "ID_ALEGACAO", nullable = false, updatable = false, insertable = false)
   private TipoAlegacaoEntity tipoAlegacao;

   @Column(name = "TEXTO_ALEGACAO", nullable = false)
   private String textoAlegacao;

   @Column(name = "ID_GRUPO_TRABALHO", nullable = false)
   private Long idGrupoTrabalho;

   @ManyToOne
   @JoinColumn(name = "ID_GRUPO_TRABALHO", updatable = false, insertable = false)
   @XmlTransient
   private GrupoTrabalhoEntity grupoTrabalho;

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
    * @return the idTipoAlegacao
    */
   public Long getIdTipoAlegacao()
   {
      return idTipoAlegacao;
   }

   /**
    * @param idTipoAlegacao the idTipoAlegacao to set
    */
   public void setIdTipoAlegacao(Long idTipoAlegacao)
   {
      this.idTipoAlegacao = idTipoAlegacao;
   }

   /**
    * @return the tipoAlegacao
    */
   public TipoAlegacaoEntity getTipoAlegacao()
   {
      return tipoAlegacao;
   }

   /**
    * @param tipoAlegacao the tipoAlegacao to set
    */
   public void setTipoAlegacao(TipoAlegacaoEntity tipoAlegacao)
   {
      this.tipoAlegacao = tipoAlegacao;
   }

   /**
    * @return the textoAlegacao
    */
   public String getTextoAlegacao()
   {
      return textoAlegacao;
   }

   /**
    * @param textoAlegacao the textoAlegacao to set
    */
   public void setTextoAlegacao(String textoAlegacao)
   {
      this.textoAlegacao = textoAlegacao;
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

   public AlegacaoGTEntity clone(GrupoTrabalhoEntity grupoTrabalhoEntity)
   {
      AlegacaoGTEntity entity = new AlegacaoGTEntity();
      entity.setTipoAlegacao(this.tipoAlegacao);
      entity.setIdTipoAlegacao(this.tipoAlegacao.getId());
      entity.setTextoAlegacao(this.textoAlegacao);
      entity.setIdGrupoTrabalho(grupoTrabalhoEntity.getId());
      entity.setGrupoTrabalho(grupoTrabalhoEntity);

      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }
}
