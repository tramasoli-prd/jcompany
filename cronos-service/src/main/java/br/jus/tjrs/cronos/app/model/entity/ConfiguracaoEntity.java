package br.jus.tjrs.cronos.app.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "CONFIGURACAO", schema = "CRONOS")
@SequenceGenerator(name = "CONFIGURACAO_GENERATOR", sequenceName = "SEQ_CONFIGURACAO", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfiguracaoEntity extends VersionedEntity<Long> implements LogicalExclusion
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONFIGURACAO_GENERATOR")
   @Column(name = "PK_CONFIGURACAO_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @OneToOne
   @JoinColumn(name = "ID_GRUPO_TRABALHO", updatable = false, insertable = false)
   @XmlTransient
   private GrupoTrabalhoEntity grupoTrabalho;

   @Column(name = "ID_GRUPO_TRABALHO")
   private Long idGrupoTrabalho;

   @Enumerated(EnumType.STRING)
   @Column(name = "PERMITE_EDITAR_MENU")
   private SimNao permiteEditarMenu;

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

   /**
    * @return the permiteEditarMenu
    */
   public SimNao getPermiteEditarMenu()
   {
      return permiteEditarMenu;
   }

   /**
    * @param permiteEditarMenu the permiteEditarMenu to set
    */
   public void setPermiteEditarMenu(SimNao permiteEditarMenu)
   {
      this.permiteEditarMenu = permiteEditarMenu;
   }

   public ConfiguracaoEntity clone(GrupoTrabalhoEntity grupoTrabalhoEntity)
   {
      ConfiguracaoEntity entity = new ConfiguracaoEntity();
      entity.setPermiteEditarMenu(this.permiteEditarMenu);
      // --
      entity.setIdGrupoTrabalho(grupoTrabalhoEntity.getId());
      entity.setGrupoTrabalho(grupoTrabalhoEntity);
      // --
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }
}
