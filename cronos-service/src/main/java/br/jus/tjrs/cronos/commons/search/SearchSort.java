package br.jus.tjrs.cronos.commons.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchSort
{
   private static final Pattern SORT_SPLIT = Pattern.compile("[,]");
   private static final Pattern SORT_PATTERN = Pattern.compile("(\\w+)(?:\\s*[:|\\s]\\s*(ASC|DESC))?",
            Pattern.CASE_INSENSITIVE);

   public static final String ASC = "ASC";
   public static final String DESC = "DESC";

   public static List<SearchSort> split(String order)
   {
      if (order != null && !order.isEmpty())
      {
         List<SearchSort> list = new LinkedList<SearchSort>();

         for (String part : SORT_SPLIT.split(order))
         {
            SearchSort sort = from(part);
            if (sort != null)
            {
               list.add(sort);
            }
         }
         return list;
      }
      return Collections.emptyList();
   }

   public static SearchSort from(String order)
   {
      if (order == null || order.trim().isEmpty())
      {
         return null;
      }
      return new SearchSort(order);
   }

   private String name;

   private String order;

   private SearchSort(String sort)
   {
      Matcher matcher = SORT_PATTERN.matcher(sort.trim());

      if (matcher.matches())
      {
         this.name = matcher.group(1).trim();
         this.order = matcher.group(2) != null ? matcher.group(2).trim().toUpperCase() : ASC;
      }
      else
      {
         throw new IllegalArgumentException(String.format("Invalid sort pattern \"%s\" expected \"name [asc|desc]?\"",
                  sort));
      }
   }

   public String getName()
   {
      return name;
   }

   public String getOrder()
   {
      return order;
   }

   @Override
   public String toString()
   {
      return name + " " + order;
   }

   public static void main(String[] args)
   {
      for (String string : Arrays.asList("nome ASC", "nome  ASC", "nome:ASC", "nome : ASC"))
      {
         try
         {
            System.out.println(string + " -> " + SearchSort.from(string));
         }
         catch (Exception e)
         {
            System.err.println(string + " -> " + e.getMessage());
         }
      }
   }

}