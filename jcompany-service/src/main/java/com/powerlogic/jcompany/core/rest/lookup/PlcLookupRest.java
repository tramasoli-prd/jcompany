package com.powerlogic.jcompany.core.rest.lookup;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.beanutils.BeanUtils;

import com.powerlogic.jcompany.core.commons.config.PlcConfiguration;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.model.service.IPlcEntityService;
import com.powerlogic.jcompany.core.rest.PlcAbstractRest;
import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticated;

@PlcAuthenticated
@Path("/entity/lookup")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class PlcLookupRest extends PlcAbstractRest{


	@Inject
	private PlcConfiguration config;

	@Inject 
	private BeanManager bm;

	@GET
	@Path("/{key}")
	public Response findLookup(@Context HttpHeaders hh, @Context Request request, @PathParam("key") String key) throws PlcException {


		String clazz = config.get(key);

		if (clazz!=null) { 
			Class c;
			try {
				c = Class.forName(clazz);
			} catch (ClassNotFoundException e) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			String jsonString = "";
			if (c.isEnum()) {

				return trataEnum(request, key, c);

			} else {

				Bean bean = null;
				for(Bean b : bm.getBeans(IPlcEntityService.class)) {
					if (b.getBeanClass().getCanonicalName().endsWith(c.getSimpleName().replace("Entity", "Service")+"Impl")) {
						bean = b;
						break;
					}
				}
				if (bean!=null) {
					CreationalContext ctx = bm.createCreationalContext(bean);
					IPlcEntityService entityService = (IPlcEntityService)bm.getReference(bean, IPlcEntityService.class, ctx);

					List lista = entityService.findAll();

					EntityTag etag = new EntityTag(key);

					Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(etag);

					if (responseBuilder != null) {
						return responseBuilder.build();
					}

					responseBuilder = Response.ok(lista).tag(etag);

					return responseBuilder.build();		
				} else {
					return Response.status(Status.NOT_FOUND).build();
				}
			}


		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	private Response trataEnum(Request request, String key, Class c) {
		String jsonString;
		// [{"sexo":"M","descricao":"Masculino"},{"sexo":"F","descricao":"Feminino"}]
		jsonString = "[";
		for(Object o : c.getEnumConstants()) {
			String label="";
			try {
				label = BeanUtils.getProperty(o, "label");
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			jsonString = jsonString.concat("{\""+key+"\":\""+o+"\",\"descricao\":\""+label+"\"},");
		}
		if (c.getEnumConstants().length>0) {
			jsonString = jsonString.substring(0, jsonString.length()-1);
		}
		jsonString = jsonString.concat("]");
		return cache(request, jsonString, c.getSimpleName());
	}


}
