package br.jus.tjrs.cronos.core.model.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public class SpecificationEntity
{
   public static <T, V> Specification<T> equal(final SingularAttribute<T, V> field, final V value)
   {
      return new Specification<T>()
      {
         @Override
         public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
         {
            return cb.equal(root.get(field), value);
         }
      };
   }
}
