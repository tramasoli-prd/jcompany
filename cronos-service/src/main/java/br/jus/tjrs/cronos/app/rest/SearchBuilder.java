package br.jus.tjrs.cronos.app.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;

import br.jus.tjrs.cronos.commons.search.Pagination;

public class SearchBuilder
{
   @PathParam("filter")
   private PathSegment filter;

   @DefaultValue("0")
   @QueryParam("page")
   private int page;

   @DefaultValue("10")
   @QueryParam("limit")
   private int limit;

   @QueryParam("order")
   private String order;

   public <T> Pagination<T> build(Class<T> type)
   {
      Pagination<T> options = Pagination.to(type);

      if (filter != null)
      {
         MultivaluedMap<String, String> matrixParameters = filter.getMatrixParameters();
         for (String param : matrixParameters.keySet())
         {
            options.filter(param, matrixParameters.getFirst(param));
         }
      }

      options.paged(page, limit);
      options.orderBy(order);

      return options;
   }
}
