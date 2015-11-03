package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;

public class CarregarCategoriaDTO
{

   private TipoArvore tipoArvore;
   private Long idCategoria;
   private Long idGrupoTrabalho;

   /**
    * @return the tipoArvore
    */
   public TipoArvore getTipoArvore()
   {
      return tipoArvore;
   }

   /**
    * @param tipoArvore the tipoArvore to set
    */
   public void setTipoArvore(TipoArvore tipoArvore)
   {
      this.tipoArvore = tipoArvore;
   }

   /**
    * @return the idCategoria
    */
   public Long getIdCategoria()
   {
      return idCategoria;
   }

   /**
    * @param idCategoria the idCategoria to set
    */
   public void setIdCategoria(Long idCategoria)
   {
      this.idCategoria = idCategoria;
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

}
