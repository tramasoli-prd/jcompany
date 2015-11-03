package br.jus.tjrs.cronos.core.messages;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class MessageEntry
{
   private MessageKey key;

   private MessageType type;

   private String message;

   private transient String[] args;

   public MessageEntry(MessageKey key, MessageType type)
   {
      this.key = key;
      this.type = type;
   }

   public MessageEntry(MessageKey key, MessageType type, String... args)
   {
      this(key, type);
      this.args = args;
   }

   public MessageType getType()
   {
      return type;
   }

   public MessageKey getKey()
   {
      return key;
   }

   public String getMessage()
   {
      if (message != null && !message.isEmpty())
      {
         return message;
      }
      return key.getName();
   }

   void setMessage(String message)
   {
      this.message = message;
   }

   @XmlTransient
   public String[] getArgs()
   {
      return args;
   }

   void setArgs(String[] args)
   {
      this.args = args;
   }

   @Override
   public String toString()
   {
      return getMessage();
   }
}
