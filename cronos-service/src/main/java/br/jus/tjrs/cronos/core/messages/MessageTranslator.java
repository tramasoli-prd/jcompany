package br.jus.tjrs.cronos.core.messages;

import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import br.jus.tjrs.cronos.app.util.ConstantUtil;

public abstract class MessageTranslator
{
   private static final String MESSAGE_ARG = "%s";

   private static class StringMessageKey implements MessageKey
   {
      private String name;

      public StringMessageKey(String name)
      {
         this.name = name;
      }

      @Override
      public String getName()
      {
         return name;
      }

      public static MessageType getType(String messageKey)
      {
         MessageType type = MessageType.valueOrNull(StringUtils.substringBefore(messageKey, ConstantUtil.DOT));

         if (type == null)
         {
            type = MessageType.INFO;
         }
         return type;
      }
   }

   private MessageMap allMessages;

   private final String messageName;

   public MessageTranslator(String messageName)
   {
      this.messageName = messageName;
      this.allMessages = getAllMessages(getBundle());
   }

   public MessageMap getAllMessages()
   {
      return allMessages;
   }

   private MessageMap getAllMessages(ResourceBundle bundle)
   {
      MessageMap messageMap = new MessageMap();
      Enumeration<String> keys = bundle.getKeys();
      while (keys.hasMoreElements())
      {
         messageMap.add(createMessage(bundle, keys.nextElement()));
      }
      return messageMap;
   }

   private MessageEntry createMessage(ResourceBundle bundle, String messageKey)
   {
      String messageString = bundle.getString(messageKey);

      MessageEntry messageEntry = new MessageEntry(new StringMessageKey(messageKey),
               StringMessageKey.getType(messageString));

      messageEntry.setMessage(messageString);

      return messageEntry;
   }

   public MessageMap translate(MessageMap messageMap)
   {
      ResourceBundle bundle = getBundle();

      for (Entry<MessageType, List<MessageEntry>> entry : messageMap.getMessages().entrySet())
      {
         translate(bundle, entry.getValue());
      }
      return messageMap;
   }

   public MessageEntry translate(MessageEntry entry)
   {
      ResourceBundle bundle = getBundle();
      translateMessage(bundle, entry);
      return entry;
   }

   protected void translate(ResourceBundle bundle, List<MessageEntry> value)
   {
      for (MessageEntry message : value)
      {
         translateMessage(bundle, message);
      }
   }

   protected void translateMessage(ResourceBundle bundle, MessageEntry message)
   {
      message.setMessage(getMessage(bundle, message));
   }

   protected String getMessage(ResourceBundle bundle, MessageEntry message)
   {
      String messageString = getString(bundle, message.getType().lowerName() + "." + message.getKey().getName());

      if (StringUtils.isBlank(messageString))
      {
         messageString = getString(bundle, message.getKey().getName());
      }
      if (StringUtils.isBlank(messageString))
      {
         messageString = message.getKey().getName();
      }

      if (messageString.contains(MESSAGE_ARG))
      {
         return String.format(messageString, (Object[]) message.getArgs());
      }
      return messageString;
   }

   private ResourceBundle getBundle()
   {
      return ResourceBundle.getBundle(messageName);
   }

   private String getString(ResourceBundle bundle, String key)
   {
      try
      {
         return bundle.getString(key);
      }
      catch (Exception e)
      {
         return null;
      }
   }
}
