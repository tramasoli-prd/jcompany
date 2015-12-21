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
 * Enum que representa a situação do registro na base de dados.
 * Dominio discreto para valores tipo 'A', 'I' e 'E'. 
 * Utilizado para manipular a propriedade na entidade que representa a exclusão lógica.
 * 
 * Permite alteração simples para novos valores, mantendo a integridade dos valores que são utilizados de controles.
 * 
 * @since 1.0.0
 * @author Powerlogic
 *  
 */
public enum PlcSituacao {

   A /* situacao.A=Ativo */,
   I /* situacao.I=Inativo */,
   E /* situacao.E=Excluido */;

   /**
    * @return Retorna o codigo.
    */
   public String getCodigo()
   {
      return this.toString();
   }

}
