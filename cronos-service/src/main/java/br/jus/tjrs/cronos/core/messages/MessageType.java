package br.jus.tjrs.cronos.core.messages;

public enum MessageType
{
   SUCCESS, INFO, WARNING, ERROR;

   private String lowerName;

   private MessageType()
   {
      lowerName = name().toLowerCase();
   }

   public String lowerName()
   {
      return lowerName;
   }

   public static MessageType valueOrNull(String name)
   {
      for (MessageType type : values())
      {
         if (type.name().equalsIgnoreCase(name))
         {
            return type;
         }
      }
      return null;
   }
}
