package br.jus.tjrs.cronos.app.model.dto;

import java.util.ArrayList;
import java.util.List;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;

public class RetornoPesquisaGeralDTO
{

   private Long id;
   private String titulo;
   private TipoArvore arvore;
   private int versao;
   private List<LabelDTO> label = new ArrayList<LabelDTO>();
   private Boolean excluir;
   private Boolean editar;
   private Boolean revisar;
   private Boolean adicionar;
   private Boolean visualizar;

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
    * @return the titulo
    */
   public String getTitulo()
   {
      return titulo;
   }

   /**
    * @param titulo the titulo to set
    */
   public void setTitulo(String titulo)
   {
      this.titulo = titulo;
   }

   /**
    * @return the arvore
    */
   public TipoArvore getArvore()
   {
      return arvore;
   }

   /**
    * @param arvore the arvore to set
    */
   public void setArvore(TipoArvore arvore)
   {
      this.arvore = arvore;
   }

   /**
    * @return the versao
    */
   public int getVersao()
   {
      return versao;
   }

   /**
    * @param versao the versao to set
    */
   public void setVersao(int versao)
   {
      this.versao = versao;
   }

   /**
    * @return the label
    */
   public List<LabelDTO> getLabel()
   {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(List<LabelDTO> label)
   {
      this.label = label;
   }

   /**
    * @return the excluir
    */
   public Boolean getExcluir()
   {
      return excluir;
   }

   /**
    * @param excluir the excluir to set
    */
   public void setExcluir(Boolean excluir)
   {
      this.excluir = excluir;
   }

   /**
    * @return the editar
    */
   public Boolean getEditar()
   {
      return editar;
   }

   /**
    * @param editar the editar to set
    */
   public void setEditar(Boolean editar)
   {
      this.editar = editar;
   }

   /**
    * @return the revisar
    */
   public Boolean getRevisar()
   {
      return revisar;
   }

   /**
    * @param revisar the revisar to set
    */
   public void setRevisar(Boolean revisar)
   {
      this.revisar = revisar;
   }

   /**
    * @return the adicionar
    */
   public Boolean getAdicionar()
   {
      return adicionar;
   }

   /**
    * @param adicionar the adicionar to set
    */
   public void setAdicionar(Boolean adicionar)
   {
      this.adicionar = adicionar;
   }

   /**
    * @return the visualizar
    */
   public Boolean getVisualizar()
   {
      return visualizar;
   }

   /**
    * @param visualizar the visualizar to set
    */
   public void setVisualizar(Boolean visualizar)
   {
      this.visualizar = visualizar;
   }

}
