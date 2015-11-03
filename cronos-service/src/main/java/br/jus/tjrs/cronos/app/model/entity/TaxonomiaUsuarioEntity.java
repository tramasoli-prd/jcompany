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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.domain.Permissoes;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "TAXONOMIA_USUARIO", schema = "CRONOS")
@SequenceGenerator(name = "TAXONOMIA_USUARIO_GENERATOR", sequenceName = "SEQ_TAXONOMIA_USUARIO", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxonomiaUsuarioEntity extends VersionedEntity<Long> implements LogicalExclusion,
         Comparable<TaxonomiaUsuarioEntity>
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAXONOMIA_USUARIO_GENERATOR")
   @Column(name = "PK_TAXONOMIA_USU_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "ID_USUARIO", updatable = false, insertable = false)
   private UsuarioEntity usuario;

   @Column(name = "ID_USUARIO")
   private Long idUsuario;

   @ManyToOne
   @JoinColumn(name = "ID_GT", updatable = false, insertable = false)
   @XmlTransient
   private GrupoTrabalhoEntity grupoTrabalho;

   @Column(name = "ID_GT")
   private Long idGrupoTrabalho;

   @Column(name = "ORDEM")
   private Integer ordem;

   @Enumerated(EnumType.STRING)
   @Column(name = "PADRAO")
   private SimNao padrao;

   @Enumerated(EnumType.STRING)
   @Column(name = "PERMISSOES", nullable = true, length = 1)
   private Permissoes permissoes;

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
    * @return the usuario
    */
   public UsuarioEntity getUsuario()
   {
      return usuario;
   }

   /**
    * @param usuario the usuario to set
    */
   public void setUsuario(UsuarioEntity usuario)
   {
      this.usuario = usuario;
   }

   /**
    * @return the idUsuario
    */
   public Long getIdUsuario()
   {
      return idUsuario;
   }

   /**
    * @param idUsuario the idUsuario to set
    */
   public void setIdUsuario(Long idUsuario)
   {
      this.idUsuario = idUsuario;
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
    * @return the ordem
    */
   public Integer getOrdem()
   {
      return ordem;
   }

   /**
    * @param ordem the ordem to set
    */
   public void setOrdem(Integer ordem)
   {
      this.ordem = ordem;
   }

   /**
    * @return the padrao
    */
   public SimNao getPadrao()
   {
      return padrao;
   }

   /**
    * @param padrao the padrao to set
    */
   public void setPadrao(SimNao padrao)
   {
      this.padrao = padrao;
   }

   /**
    * @return the permissoes
    */
   public Permissoes getPermissoes()
   {
      return permissoes;
   }

   /**
    * @param permissoes the permissoes to set
    */
   public void setPermissoes(Permissoes permissoes)
   {
      this.permissoes = permissoes;
   }

   public TaxonomiaUsuarioEntity clone(UsuarioEntity usuario, GrupoTrabalhoEntity grupoTrabalho)
   {
      TaxonomiaUsuarioEntity entity = new TaxonomiaUsuarioEntity();
      entity.setOrdem(this.ordem);
      entity.setUsuario(usuario);
      entity.setGrupoTrabalho(grupoTrabalho);
      entity.setPermissoes(this.permissoes);
      entity.setPadrao(this.padrao);
      entity.setIdUsuario(usuario.getId());
      entity.setIdGrupoTrabalho(grupoTrabalho.getId());
      // default
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }

   @Override
   public int compareTo(TaxonomiaUsuarioEntity o)
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
