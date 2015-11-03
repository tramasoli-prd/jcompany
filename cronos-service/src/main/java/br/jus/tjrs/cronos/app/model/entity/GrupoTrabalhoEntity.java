package br.jus.tjrs.cronos.app.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "GRUPO_DE_TRABALHO", schema = "CRONOS")
@SequenceGenerator(name = "GRUPO_TRABALHO_GENERATOR", sequenceName = "SEQ_GRUPO_TRABALHO", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
         @NamedQuery(name = "GrupoTrabalhoEntity.findByUsuarioAndSituacao", query = "SELECT g FROM GrupoTrabalhoEntity g WHERE g.usuario.id = :usuario AND g.situacao = :situacao")
})
public class GrupoTrabalhoEntity extends VersionedEntity<Long> implements LogicalExclusion
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRUPO_TRABALHO_GENERATOR")
   @Column(name = "PK_GRUPO_DE_TRABALHO_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "ORDEM")
   private int ordem;

   @Column(name = "ID_ICONE_EDOCS")
   private Long iconeEdocs;

   @Column(name = "ICONE_PADRAO", length = 30)
   private Long iconePadrao;

   @OneToOne
   @JoinColumn(name = "ID_USUARIO")
   @XmlTransient
   private UsuarioEntity usuario;

   @OneToMany(mappedBy = "grupoTrabalho")
   private List<CategoriaItemEntity> itens = new ArrayList<CategoriaItemEntity>();

   @OneToMany(mappedBy = "grupoTrabalho")
   private List<AlegacaoGTEntity> alegacoes = new ArrayList<AlegacaoGTEntity>();

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public int getOrdem()
   {
      return ordem;
   }

   public void setOrdem(int ordem)
   {
      this.ordem = ordem;
   }

   public Long getIconeEdocs()
   {
      return iconeEdocs;
   }

   public void setIconeEdocs(Long iconeEdocs)
   {
      this.iconeEdocs = iconeEdocs;
   }

   public Long getIconePadrao()
   {
      return iconePadrao;
   }

   public void setIconePadrao(Long iconePadrao)
   {
      this.iconePadrao = iconePadrao;
   }

   public UsuarioEntity getUsuario()
   {
      return usuario;
   }

   public void setUsuario(UsuarioEntity usuario)
   {
      this.usuario = usuario;
   }

   public List<CategoriaItemEntity> getItens()
   {
      return itens;
   }

   public void setItens(List<CategoriaItemEntity> itens)
   {
      this.itens = itens;
   }

   /**
    * @return the alegacoes
    */
   public List<AlegacaoGTEntity> getAlegacoes()
   {
      return alegacoes;
   }

   /**
    * @param alegacoes the alegacoes to set
    */
   public void setAlegacoes(List<AlegacaoGTEntity> alegacoes)
   {
      this.alegacoes = alegacoes;
   }

   /**
    * Efetua o clone apenas dos atributos persistidos nesta entidade
    * */
   public GrupoTrabalhoEntity clone(UsuarioEntity usuarioEntity)
   {
      GrupoTrabalhoEntity entity = new GrupoTrabalhoEntity();
      entity.setIconeEdocs(this.iconeEdocs);
      entity.setIconePadrao(this.iconePadrao);
      entity.setOrdem(ordem);
      entity.setUsuario(usuarioEntity);

      // default
      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }
}
