package br.jus.tjrs.cronos.app.model.domain;

public enum TipoArvore
{
   T("Tópico"), // T�pico
   M("Modelo"), // Modelo
   S("Senten�a"); // Senten�a

   private final String name;

   private TipoArvore(String s)
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
}
