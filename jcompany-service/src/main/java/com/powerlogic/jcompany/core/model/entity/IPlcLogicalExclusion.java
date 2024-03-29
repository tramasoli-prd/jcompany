/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.entity;

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;

/**
 * 
 * Interface para utilizar a Exclusão Lógica
 * 
 * @author Powerlogic
 * @category Entity
 * @since 1.0.0
 */
public interface IPlcLogicalExclusion {

	public PlcSituacao getSituacao();
	public void setSituacao(PlcSituacao situacao);
	
}
