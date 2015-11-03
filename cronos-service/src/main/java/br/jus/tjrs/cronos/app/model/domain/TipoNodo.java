package br.jus.tjrs.cronos.app.model.domain;

public enum TipoNodo
{
   N("NODO"), // Nodo
   I("ITEM"), // Item
   E("ETIQUETA");// Etiqueta

   private final String name;

   private TipoNodo(String s)
   {
      name = s;
   }

   public boolean equalsName(String otherName)
   {
      return (otherName == null) ? false : name.equals(otherName);
   }

   public String toString()
   {
      return name;
   }

   /**
    * @return Retorna o codigo.
    */
   public String getCodigo()
   {
      return this.toString();
   }

}
