package br.jus.tjrs.cronos.core.rest.entity;

import java.io.Serializable;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.commons.search.PagedResult;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.entity.EntityModel;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EntityModelRest<PK extends Serializable, T extends EntityModel<PK>, A>
{
   @GET
   @Path("/find")
   PagedResult<T> find(@BeanParam A searchBuilder) throws CronosException;

   @GET
   @Path("/create")
   T newEntity() throws CronosException;

   @GET
   @Path("/get/{id}")
   T get(@PathParam("id") PK entityId) throws CronosException;

   @POST
   @Path("/save")
   T save(T entity) throws CronosException;

   @POST
   @Path("/remove")
   boolean remove(T entity) throws CronosException;
}
