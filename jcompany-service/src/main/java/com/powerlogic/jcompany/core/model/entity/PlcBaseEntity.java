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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Access(AccessType.PROPERTY)
public abstract class PlcBaseEntity<PK extends Serializable> implements IPlcBaseEntity<PK> {
	
	private static final long serialVersionUID = 1L;
 
 
	@Override
	public boolean isIdSet() {
		if(getId()!=null)
			return true;
		return false;
	}
	
}
