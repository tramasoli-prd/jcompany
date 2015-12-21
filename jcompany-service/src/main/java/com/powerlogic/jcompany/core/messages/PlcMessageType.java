/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.messages;

/**
 * 
 * Enum para Definir os tipos de Mensagens disparadas durante a execução da requisição.
 * 
 *	- SUCCESS: 	Mensagem de Sucesso 	- Cor Azul
 *	- INFO:		Informações 			- Cor Verde
 *	- WARNING 	Alerta 					- Cor Amarela
 *	- ERROR: 	Mensagem de Erro 		- Cor Vermelha
 *
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public enum PlcMessageType {
	
   SUCCESS, INFO, WARNING, ERROR;

   private String lowerName;

   private PlcMessageType() {
      lowerName = name().toLowerCase();
   }

   public String lowerName() {
      return lowerName;
   }

   public static PlcMessageType valueOrNull(String name) {
	   
      for (PlcMessageType type : values())
      {
         if (type.name().equalsIgnoreCase(name))
         {
            return type;
         }
      }
      return null;
   }
}
