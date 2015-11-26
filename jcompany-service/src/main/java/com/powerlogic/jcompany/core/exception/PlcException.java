package com.powerlogic.jcompany.core.exception;

import javax.ejb.ApplicationException;

import com.powerlogic.jcompany.core.messages.IPlcMessageKey;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.messages.PlcMessageType;

@ApplicationException(rollback = true)
public class PlcException extends RuntimeException
{
   private static final long serialVersionUID = 1L;

   private PlcMessageMap messageMap;

   public PlcException(IPlcMessageKey key)
   {
      addMessage(key);
   }

   public PlcException(IPlcMessageKey key, String... args)
   {
      addMessage(key, args);
   }

   public PlcException(Throwable cause, IPlcMessageKey key)
   {
      super(cause);
      addMessage(key);
   }

   public PlcException(Throwable cause, IPlcMessageKey key, String... args)
   {
      super(cause);
      addMessage(key, args);
      addThrowable(cause);
   }

   public PlcException(PlcMessageMap messageMap)
   {
      this.messageMap = messageMap;
   }

   public PlcException(Throwable cause, PlcMessageMap messageMap)
   {
      super(cause);
      this.messageMap = messageMap;
   }

   protected void addMessage(IPlcMessageKey key, String... args)
   {
      getMessageMap().addMessage(key, PlcMessageType.ERROR, args);
   }

   protected void addThrowable(Throwable throwable)
   {
      if (throwable != this && throwable instanceof PlcException)
      {
         getMessageMap().addAll(((PlcException) throwable).getMessageMap());
      }
   }

   public PlcMessageMap getMessageMap()
   {
      if (messageMap == null)
      {
         messageMap = new PlcMessageMap();
      }
      return messageMap;
   }
}
