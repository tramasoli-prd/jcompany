package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;

public class AutoCompleteDTO
{

   private Long idGrupoTrabalho;
   private String part;
   private TipoArvore tipoArvore = TipoArvore.T;

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
    * @return the part
    */
   public String getPart()
   {
      return part;
   }

   /**
    * @param part the part to set
    */
   public void setPart(String part)
   {
      this.part = part;
   }

}
