package br.jus.tjrs.cronos.app.model.dto;

public class CarregarProcessoDTO
{

   private String perfil;
   private String numeroProcesso;
   private String comarca;

   /**
    * @return the perfil
    */
   public String getPerfil()
   {
      return perfil;
   }

   /**
    * @param perfil the perfil to set
    */
   public void setPerfil(String perfil)
   {
      this.perfil = perfil;
   }

   /**
    * @return the numeroProcesso
    */
   public String getNumeroProcesso()
   {
      return numeroProcesso;
   }

   /**
    * @param numeroProcesso the numeroProcesso to set
    */
   public void setNumeroProcesso(String numeroProcesso)
   {
      this.numeroProcesso = numeroProcesso;
   }

   /**
    * @return the comarca
    */
   public String getComarca()
   {
      return comarca;
   }

   /**
    * @param comarca the comarca to set
    */
   public void setComarca(String comarca)
   {
      this.comarca = comarca;
   }

}
