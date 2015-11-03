/*
 * Copyright 2008-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.jus.tjrs.cronos.core.model.repository.specification;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class Specifications<T> implements Specification<T>, Serializable
{
   private static final long serialVersionUID = 1L;

   private final Specification<T> spec;

   private Specifications(Specification<T> spec)
   {
      this.spec = spec;
   }

   public static <T> Specifications<T> where(Specification<T> spec)
   {
      return new Specifications<T>(spec);
   }

   public Specifications<T> and(Specification<T> other)
   {
      return new Specifications<T>(new ComposedSpecification<T>(spec, other)
      {
         private static final long serialVersionUID = 1L;

         @Override
         protected Predicate combine(CriteriaBuilder builder, Predicate thisPredicate, Predicate otherPredicate)
         {
            return builder.and(thisPredicate, otherPredicate);
         }
      });
   }

   public Specifications<T> or(Specification<T> other)
   {
      return new Specifications<T>(new ComposedSpecification<T>(spec, other)
      {
         private static final long serialVersionUID = 1L;

         @Override
         protected Predicate combine(CriteriaBuilder builder, Predicate thisPredicate, Predicate otherPredicate)
         {
            return builder.or(thisPredicate, otherPredicate);
         }
      });
   }

   public static <T> Specifications<T> not(Specification<T> spec)
   {
      return new Specifications<T>(new NegatedSpecification<T>(spec));
   }

   public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
   {
      return spec == null ? null : spec.toPredicate(root, query, builder);
   }

   private static class NegatedSpecification<T> implements Specification<T>, Serializable
   {

      private static final long serialVersionUID = 1L;

      private final Specification<T> spec;

      public NegatedSpecification(Specification<T> spec)
      {
         this.spec = spec;
      }

      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
      {
         return spec == null ? null : builder.not(spec.toPredicate(root, query, builder));
      }
   }

   private static abstract class ComposedSpecification<T> implements Specification<T>, Serializable
   {

      private static final long serialVersionUID = 1L;

      private final Specification<T> lhs;
      private final Specification<T> rhs;

      private ComposedSpecification(Specification<T> lhs, Specification<T> rhs)
      {
         this.lhs = lhs;
         this.rhs = rhs;
      }

      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder)
      {

         Predicate otherPredicate = rhs == null ? null : rhs.toPredicate(root, query, builder);
         Predicate thisPredicate = lhs == null ? null : lhs.toPredicate(root, query, builder);

         if (thisPredicate == null)
         {
            return otherPredicate;
         }
         else if (otherPredicate == null)
         {
            return thisPredicate;
         }
         return combine(builder, thisPredicate, otherPredicate);
      }

      protected abstract Predicate combine(CriteriaBuilder builder, Predicate thisPredicate, Predicate otherPredicate);
   }
}