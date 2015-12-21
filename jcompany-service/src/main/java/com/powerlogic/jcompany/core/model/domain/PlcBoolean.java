/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.domain;

/**
 * 
 * Enum para Manipulação de Objetos de tipo (Sim/Não)
 * Dominio discreto para valores tipo 'S' ou 'N'. 
 * Melhor que usar boolean porque permite alteração simples para novos valores, e permite três estados (null, N ou S)
 * 
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public enum PlcBoolean {

   Y /* plcBoolean.Y=Sim */,
   N /* plcBoolean.N=Não */;

   /**
    * @return Retorna o codigo.
    */
   public String getCodigo()
   {
      return this.toString();
   }

}
