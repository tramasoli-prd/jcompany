/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

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
