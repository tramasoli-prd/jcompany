package br.jus.tjrs.cronos.core.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageMap
{
   private Map<MessageType, List<MessageEntry>> messages = new HashMap<>();

   public Map<MessageType, List<MessageEntry>> getMessages()
   {
      return messages;
   }

   public void setMessages(Map<MessageType, List<MessageEntry>> messages)
   {
      this.messages = messages;
   }

   public void add(MessageEntry message)
   {
      getTypeList(message.getType()).add(message);
   }

   public void addAll(MessageMap messageMap)
   {
      for (Entry<MessageType, List<MessageEntry>> messages : messageMap.messages.entrySet())
      {
         getTypeList(messages.getKey()).addAll(messages.getValue());
      }
   }

   public void addAll(Collection<MessageEntry> messages)
   {
      for (MessageEntry message : messages)
      {
         add(message);
      }
   }

   public boolean contains(MessageType type)
   {
      List<MessageEntry> list = messages.get(type);
      return list != null && !list.isEmpty();
   }

   public List<MessageEntry> get(MessageType type)
   {
      List<MessageEntry> list = messages.get(type);
      if (list == null || list.isEmpty())
      {
         return Collections.emptyList();
      }
      return new ArrayList<>(list);
   }

   public void clear()
   {
      messages.clear();
   }

   public void addMessage(MessageKey key, MessageType type)
   {
      addMessage(key, type, null);
   }

   public void addMessage(MessageKey key, MessageType type, String[] args)
   {
      add(new MessageEntry(key, type, args));
   }

   protected List<MessageEntry> getTypeList(MessageType type)
   {
      List<MessageEntry> list = messages.get(type);

      if (list == null)
      {
         messages.put(type, list = new LinkedList<MessageEntry>());
      }
      return list;
   }
}
