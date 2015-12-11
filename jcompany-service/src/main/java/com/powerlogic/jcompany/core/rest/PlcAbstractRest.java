package com.powerlogic.jcompany.core.rest;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public abstract class PlcAbstractRest
{
   @GET
   public Response index()
   {
      return responseText("REST-API: " + getClass()).build();
   }

   protected ResponseBuilder responseText(Object result)
   {
      return Response.ok(result, MediaType.TEXT_PLAIN);
   }

   protected ResponseBuilder responseJson(Object result)
   {
      return Response.ok(result, MediaType.APPLICATION_JSON);
   }
   
	protected <E> Response cache(Request request, String jsonString, String name) {
		
		Date data = Calendar.getInstance().getTime();
   
		final EntityTag eTag = new EntityTag( name );
	    
		final CacheControl cacheControl = new CacheControl();
	    
		cacheControl.setMaxAge(5000);
	
		ResponseBuilder builder = request.evaluatePreconditions(data, eTag);
	
		// the resoruce's information was modified, return it
		if (builder == null) {
	        builder = Response.ok(jsonString);
		}
	
	   // the resource's information was not modified, return a 304
	   return builder.cacheControl(cacheControl).lastModified(data).tag(eTag).build();
	}
}
