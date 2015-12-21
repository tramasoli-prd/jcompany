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
import java.util.List;

import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.commons.search.PlcPagination;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/**
 * @author igor
 *
 * @param <PK>
 * @param <E>
 */
public interface IPlcEntityRepository<PK extends Serializable, E extends IPlcEntityModel<PK>> {
	
   Class<E> getEntityType();

   List<E> findAll() throws PlcException;
   
   List<E> findAll(E entity) throws PlcException;

   PlcPagedResult<E> find(PlcPagination<E> config);

   E get(PK id);

   E getDetached(PK id);

   E save(E entity) throws PlcException;

   void remove(E entity);
   
   void inative(E entity);

}