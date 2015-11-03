package br.jus.tjrs.cronos.app.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.domain.TipoNodo;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "CATEGORIA_ITEM", schema = "CRONOS")
@SequenceGenerator(name = "CATEGORIA_ITEM_GENERATOR", sequenceName = "SEQ_CATEGORIA_ITEM", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
         @NamedQuery(name = "CategoriaItemEntity.likeByEtiqueta", query = "SELECT c FROM CategoriaItemEntity c join c.taxonomiasEtiqueta t WHERE "
                  + " upper(t.etiquetaGT.etiqueta) like :etiqueta "
                  + " AND c.situacao = :situacao "
                  + " AND c.arvore = :arvore "
                  + " AND ("
                  + " c.idGrupoTrabalho = :idGrupoTrabalho"
                  + " OR c.compartilhar = :compartilhar "
                  + ") "
                  + ""),
         @NamedQuery(name = "CategoriaItemEntity.likeByElemento", query = "SELECT c FROM CategoriaItemEntity c join c.elementos e WHERE "
                  + " upper(e.texto) like :elemento "
                  + " AND c.situacao = :situacao "
                  + " AND c.arvore = :arvore "
                  + " AND ( "
                  + " c.idGrupoTrabalho = :idGrupoTrabalho "
                  + " OR c.compartilhar = :compartilhar "
                  + ")")
})
public class CategoriaItemEntity extends VersionedEntity<Long> implements LogicalExclusion
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORIA_ITEM_GENERATOR")
   @Column(name = "PK_CATEGORIA_ITEM_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "TITULO", length = 200)
   private String titulo;

   @Enumerated(EnumType.STRING)
   @Column(name = "COMPARTILHAR")
   private SimNao compartilhar;

   @Column(name = "ICONE_PADRAO", length = 30)
   private String icone;

   @Column(name = "ICONE_PADRAO_COR")
   private String cor;

   @Column(name = "ID_ICONE")
   private Long idIcone;

   @OneToOne
   @JoinColumn(name = "ID_ICONE", updatable = false, insertable = false)
   private IconesPersonalEntity iconePersonal;

   @Column(name = "ORDEM")
   private Integer ordem;

   @NotNull
   @Column(name = "TIPO")
   @Enumerated(EnumType.STRING)
   private TipoNodo tipo;

   @Column(name = "PAI")
   private Long idPai;

   @NotNull
   @Column(name = "ARVORE")
   @Enumerated(EnumType.STRING)
   private TipoArvore arvore;

   @Transient
   private String descricaoArvore;

   @OneToOne
   @JoinColumn(name = "ID_TIPO_CATEGORIA")
   private TipoCategoriaEntity tipoCategoria;

   @ManyToOne
   @JoinColumn(name = "ID_GRUPO_TRABALHO", updatable = false, insertable = false)
   @XmlTransient
   private GrupoTrabalhoEntity grupoTrabalho;

   @Column(name = "ID_GRUPO_TRABALHO")
   private Long idGrupoTrabalho;

   @ManyToOne
   @JoinColumn(name = "PAI", updatable = false, insertable = false)
   @XmlTransient
   private CategoriaItemEntity pai;

   @OneToMany(mappedBy = "pai")
   private List<CategoriaItemEntity> itens = new ArrayList<CategoriaItemEntity>();

   @OneToMany(mappedBy = "categoriaItem")
   private List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiqueta = new ArrayList<TaxonomiaEtiquetaGTEntity>();

   @OneToMany(mappedBy = "categoria")
   private List<ElementoIGTEntity> elementos = new ArrayList<ElementoIGTEntity>();

   /**
    * @return the cor
    */
   public String getCor()
   {
      return cor;
   }

   /**
    * @param cor the cor to set
    */
   public void setCor(String cor)
   {
      this.cor = cor;
   }

   /**
    * @return the idIcone
    */
   public Long getIdIcone()
   {
      return idIcone;
   }

   /**
    * @param idIcone the idIcone to set
    */
   public void setIdIcone(Long idIcone)
   {
      this.idIcone = idIcone;
   }

   /**
    * @return the iconePersonal
    */
   public IconesPersonalEntity getIconePersonal()
   {
      return iconePersonal;
   }

   /**
    * @param iconePersonal the iconePersonal to set
    */
   public void setIconePersonal(IconesPersonalEntity iconePersonal)
   {
      this.iconePersonal = iconePersonal;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public SimNao getCompartilhar()
   {
      return compartilhar;
   }

   public void setCompartilhar(SimNao compartilhar)
   {
      this.compartilhar = compartilhar;
   }

   public String getTitulo()
   {
      return titulo;
   }

   public void setTitulo(String titulo)
   {
      this.titulo = titulo;
   }

   public TipoCategoriaEntity getTipoCategoria()
   {
      return tipoCategoria;
   }

   public void setTipoCategoria(TipoCategoriaEntity tipoCategoria)
   {
      this.tipoCategoria = tipoCategoria;
   }

   public GrupoTrabalhoEntity getGrupoTrabalho()
   {
      return grupoTrabalho;
   }

   public void setGrupoTrabalho(GrupoTrabalhoEntity grupoTrabalho)
   {
      this.grupoTrabalho = grupoTrabalho;
   }

   public CategoriaItemEntity getPai()
   {
      return pai;
   }

   public void setPai(CategoriaItemEntity pai)
   {
      this.pai = pai;
   }

   public List<CategoriaItemEntity> getItens()
   {
      return itens;
   }

   public void setItens(List<CategoriaItemEntity> itens)
   {
      this.itens = itens;
   }

   public Integer getOrdem()
   {
      return ordem;
   }

   public void setOrdem(Integer ordem)
   {
      this.ordem = ordem;
   }

   public List<ElementoIGTEntity> getElementos()
   {
      return elementos;
   }

   public void setElementos(List<ElementoIGTEntity> elementos)
   {
      this.elementos = elementos;
   }

   public TipoNodo getTipo()
   {
      return tipo;
   }

   public void setTipo(TipoNodo tipo)
   {
      this.tipo = tipo;
   }

   public String getIcone()
   {
      return icone;
   }

   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   public Long getIdPai()
   {
      return idPai;
   }

   public void setIdPai(Long idPai)
   {
      this.idPai = idPai;
   }

   public TipoArvore getArvore()
   {
      return arvore;
   }

   public void setArvore(TipoArvore arvore)
   {
      this.arvore = arvore;
   }

   public String getDescricaoArvore()
   {
      return descricaoArvore;
   }

   public void setDescricaoArvore(String descricaoArvore)
   {
      this.descricaoArvore = descricaoArvore;
   }

   public Long getIdGrupoTrabalho()
   {
      return idGrupoTrabalho;
   }

   public void setIdGrupoTrabalho(Long idGrupoTrabalho)
   {
      this.idGrupoTrabalho = idGrupoTrabalho;
   }

   public List<TaxonomiaEtiquetaGTEntity> getTaxonomiasEtiqueta()
   {
      return taxonomiasEtiqueta;
   }

   public void setTaxonomiasEtiqueta(List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiqueta)
   {
      this.taxonomiasEtiqueta = taxonomiasEtiqueta;
   }

   public CategoriaItemEntity clone(GrupoTrabalhoEntity grupoTrabalhoEntity, CategoriaItemEntity categoriaItemPai)
   {
      CategoriaItemEntity entity = new CategoriaItemEntity();
      entity.setCompartilhar(this.compartilhar);
      entity.setIcone(this.icone);
      entity.setOrdem(this.ordem);
      entity.setTitulo(this.titulo);
      entity.setTipo(this.tipo);
      entity.setArvore(this.arvore);
      entity.setCor(this.cor);
      if (this.iconePersonal != null) {
         entity.setIconePersonal(this.iconePersonal);
         entity.setIdIcone(this.iconePersonal.getId());
      }
      entity.setIdGrupoTrabalho(grupoTrabalhoEntity.getId());

      if (categoriaItemPai != null)
      {
         entity.setIdPai(categoriaItemPai.getId());
      }
      entity.setGrupoTrabalho(grupoTrabalhoEntity);
      entity.setTipoCategoria(this.tipoCategoria);

      entity.setSituacao(Situacao.A);
      entity.setDataAtualizacao(new Date());
      entity.setDataCriacao(new Date());
      entity.setUsuarioAtualizacao(this.getUsuarioAtualizacao());
      return entity;
   }
}
