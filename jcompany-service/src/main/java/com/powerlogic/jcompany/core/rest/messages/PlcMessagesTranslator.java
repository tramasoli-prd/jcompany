package com.powerlogic.jcompany.core.rest.messages;

import javax.enterprise.context.ApplicationScoped;

import com.powerlogic.jcompany.core.messages.PlcMessageTranslator;

@ApplicationScoped
public class PlcMessagesTranslator extends PlcMessageTranslator
{
   private static final String RB_PLC = "PlcMessages";
   
   private static final String RB_APP = "AppMessages";

   public PlcMessagesTranslator()
   {
      super(RB_PLC, RB_APP);
   }
}
