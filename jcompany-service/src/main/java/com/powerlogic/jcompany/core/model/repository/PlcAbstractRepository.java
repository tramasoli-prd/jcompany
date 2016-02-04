/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/**
 * 
 * Base Abstrata para serviços ou objetos de negócio que fazem o acesso aos repositorios de dados.<p>
 * 
 * Injeção da Entity Manager.
 * 
 * @category Repository
 * @since 1.0.0
 * @author Powerlogic
 * 
 */
public abstract class PlcAbstractRepository<PK extends Serializable, E extends IPlcEntityModel<PK>> extends PlcBaseAbstractRepository<PK, E> {

	@PersistenceContext(unitName = "jcompany-persistence-unit")
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager()
	{
		return entityManager;
	}
}
