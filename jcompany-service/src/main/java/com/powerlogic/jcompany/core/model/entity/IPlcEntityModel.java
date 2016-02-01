/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.entity;

import java.io.Serializable;

/**
 * 
 * Interface Base para IOC a partir das chamadas REST
 * 
 * @author Powerlogic
 * @category Entity
 * @since 1.0.0
 */
public interface IPlcEntityModel<PK extends Serializable> extends Serializable {

	PK getId();

	void setId(PK id);
	
    boolean isIdSet();
}