/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest.entity;

import java.io.Serializable;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.powerlogic.jcompany.commons.util.validation.PlcValidationConstraintsDTO;
import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IPlcEntityModelRest<PK extends Serializable, E extends IPlcEntityModel<PK>, A> {

	@GET
	@Path("/find")
	PlcPagedResult<E> find(@BeanParam A searchBuilder) throws PlcException;

	@GET
	@Path("/create")
	E newEntity() throws PlcException;

	@GET
	@Path("/get/{id}")
	E get(@PathParam("id") PK entityId) throws PlcException;

	@POST
	@Path("/save")
	E save(E entity) throws PlcException;

	@POST
	@Path("/remove")
	boolean remove(E entity) throws PlcException;

	@GET
	@Path("/metadata")
	public PlcValidationConstraintsDTO getEntityBeanValidationMetadata();


}
