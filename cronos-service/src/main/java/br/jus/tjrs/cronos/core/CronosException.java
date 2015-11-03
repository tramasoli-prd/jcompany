package br.jus.tjrs.cronos.core;

import javax.ejb.ApplicationException;

import br.jus.tjrs.cronos.core.messages.MessageKey;
import br.jus.tjrs.cronos.core.messages.MessageMap;
import br.jus.tjrs.cronos.core.messages.MessageType;

@ApplicationException(rollback = true)
public class CronosException extends Exception
{
   private static final long serialVersionUID = 1L;

   private MessageMap messageMap;

   public CronosException(MessageKey key)
   {
      addMessage(key);
   }

   public CronosException(MessageKey key, String... args)
   {
      addMessage(key, args);
   }

   public CronosException(Throwable cause, MessageKey key)
   {
      super(cause);
      addMessage(key);
   }

   public CronosException(Throwable cause, MessageKey key, String... args)
   {
      super(cause);
      addMessage(key, args);
      addThrowable(cause);
   }

   public CronosException(MessageMap messageMap)
   {
      this.messageMap = messageMap;
   }

   public CronosException(Throwable cause, MessageMap messageMap)
   {
      super(cause);
      this.messageMap = messageMap;
   }

   protected void addMessage(MessageKey key, String... args)
   {
      getMessageMap().addMessage(key, MessageType.ERROR, args);
   }

   protected void addThrowable(Throwable throwable)
   {
      if (throwable != this && throwable instanceof CronosException)
      {
         getMessageMap().addAll(((CronosException) throwable).getMessageMap());
      }
   }

   public MessageMap getMessageMap()
   {
      if (messageMap == null)
      {
         messageMap = new MessageMap();
      }
      return messageMap;
   }
}
