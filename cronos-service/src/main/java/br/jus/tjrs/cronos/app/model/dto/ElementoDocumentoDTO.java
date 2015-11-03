package br.jus.tjrs.cronos.app.model.dto;

public class ElementoDocumentoDTO
{

   private String texto;
   private String titulo;
   private int recuo;
   private int ordem;

   /**
    * @return the texto
    */
   public String getTexto()
   {
      return texto;
   }

   /**
    * @param texto the texto to set
    */
   public void setTexto(String texto)
   {
      this.texto = texto;
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
    * @return the recuo
    */
   public int getRecuo()
   {
      return recuo;
   }

   /**
    * @param recuo the recuo to set
    */
   public void setRecuo(int recuo)
   {
      this.recuo = recuo;
   }

   /**
    * @return the ordem
    */
   public int getOrdem()
   {
      return ordem;
   }

   /**
    * @param ordem the ordem to set
    */
   public void setOrdem(int ordem)
   {
      this.ordem = ordem;
   }

}
