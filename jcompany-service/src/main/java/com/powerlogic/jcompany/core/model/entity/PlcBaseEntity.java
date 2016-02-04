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

import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * Ancestral para todas as classes de Entidade (Entity) da aplicação sem utilizar a Auditoria Mínima e/ou Exclusão Lógica.
 * 
 * @author Powerlogic
 * @category Entity
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class PlcBaseEntity<PK extends Serializable> implements IPlcEntityModel<PK> {
	
	private static final long serialVersionUID = 1L;
 
 
	@XmlTransient
	@Override
	public boolean isIdSet() {
		if(getId()!=null)
			return true;
		return false;
	}
	
}
