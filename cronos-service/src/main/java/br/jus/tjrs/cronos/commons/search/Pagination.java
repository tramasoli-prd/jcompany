package br.jus.tjrs.cronos.commons.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Pagination<T>
{
   private static final int DEFAULT_SEARCH_LIMIT = 50;

   public static <T> Pagination<T> to(Class<T> type)
   {
      return new Pagination<>(type);
   }

   private Class<T> type;

   private Map<String, SearchFilter> filters;

   private List<SearchSort> ordination;

   private int page = 1;

   private int limit = DEFAULT_SEARCH_LIMIT;

   private int offset = 0;

   protected Pagination(Class<T> type)
   {
      this.type = type;
   }

   public Pagination<T> limit(int size)
   {
      page = 1;
      limit = size;
      offset = 0;
      return this;
   }

   public Pagination<T> paged(int pageNumber, int pageLimit)
   {
      page = Math.max(1, pageNumber);
      limit = Math.max(0, pageLimit);
      offset = (page - 1) * limit;
      return this;
   }

   public Pagination<T> orderBy(String order)
   {
      this.ordination = SearchSort.split(order);

      return this;
   }

   public Pagination<T> filter(String name, Object value)
   {
      SearchFilter filter = SearchFilter.from(name, value);
      if (filter != null)
      {
         if (filters == null)
         {
            filters = new LinkedHashMap<String, SearchFilter>();
         }
         filters.put(filter.getName(), filter);
      }
      return this;
   }

   public Class<T> getType()
   {
      return type;
   }

   public List<SearchFilter> getFilters()
   {
      return filters == null ? Collections.<SearchFilter> emptyList() : new ArrayList<SearchFilter>(filters.values());
   }

   public List<SearchSort> getOrdination()
   {
      return ordination == null ? Collections.<SearchSort> emptyList() : new ArrayList<SearchSort>(ordination);
   }

   public int getPage()
   {
      return page;
   }

   public int getLimit()
   {
      return limit;
   }

   public int getOffset()
   {
      return offset;
   }

   @Override
   public String toString()
   {
      return "SearchOptions [type=" + type + ", filters=" + filters + ", ordination=" + ordination + ", page=" + page
               + ", limit=" + limit + ", offset=" + offset + "]";
   }
}
