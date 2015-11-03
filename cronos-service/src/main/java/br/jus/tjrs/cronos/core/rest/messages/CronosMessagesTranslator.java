package br.jus.tjrs.cronos.core.rest.messages;

import br.jus.tjrs.cronos.core.messages.MessageTranslator;

public class CronosMessagesTranslator extends MessageTranslator
{
   private static final String CRONOS_MESSAGE = "CronosMessages";

   public CronosMessagesTranslator()
   {
      super(CRONOS_MESSAGE);
   }
}
