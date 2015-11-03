package br.jus.tjrs.cronos.app.model.dto;

public class PaiDTO
{

   Long id;
   String titulo;

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

}
