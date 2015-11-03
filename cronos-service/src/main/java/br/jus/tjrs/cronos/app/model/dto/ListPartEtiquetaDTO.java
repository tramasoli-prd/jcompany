package br.jus.tjrs.cronos.app.model.dto;

public class ListPartEtiquetaDTO
{

   String part;
   Long idGrupoTrabalho;

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
