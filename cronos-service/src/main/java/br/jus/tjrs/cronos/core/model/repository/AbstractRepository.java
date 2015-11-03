package br.jus.tjrs.cronos.core.model.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.commons.search.PagedResult;
import br.jus.tjrs.cronos.commons.search.Pagination;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;
import br.jus.tjrs.cronos.core.model.repository.specification.Specification;

public abstract class AbstractRepository<PK extends Serializable, E extends VersionedEntity<PK>> implements
         EntityRepository<PK, E>
{
   protected abstract EntityManager getEntityManager();

   public abstract Class<E> getEntityType();

   /*
    * (non-Javadoc)
    * 
    * @see br.jus.tjrs.cronos.model.repository.EntityRepository#get(PK)
    */
   @Override
   public E get(PK id)
   {
      return getEntityManager().find(getEntityType(), id);
   }

   /*
    * (non-Javadoc)
    * 
    * @see br.jus.tjrs.cronos.model.repository.EntityRepository#getDetached(PK)
    */
   @Override
   public E getDetached(PK id)
   {
      E enti = getEntityManager().find(getEntityType(), id);
      getEntityManager().detach(enti);
      return enti;
   }

   /*
    * (non-Javadoc)
    * 
    * @see br.jus.tjrs.cronos.model.repository.EntityRepository#save(E)
    */
   @Override
   public E save(E entity) throws CronosException
   {
      // Implementar Listener JPA
      try
      {

         if (entity.getId() == null)
         {
            getEntityManager().persist(entity);
         }
         else
         {
            entity = getEntityManager().merge(entity);
         }
      }
      catch (OptimisticLockException e)
      {
         throw CronosErrors.REGISTROS_CONCORRENTES_023.create();
      }

      return entity;
   }

   /*
    * (non-Javadoc)
    * 
    * @see br.jus.tjrs.cronos.model.repository.EntityRepository#remove(E)
    */
   @Override
   public void remove(E entity)
   {
      if (entity instanceof LogicalExclusion)
      {
         logicalRemove(entity);
      }
      else
      {
         physicalRemove(entity);
      }
   }

   protected void logicalRemove(E entity)
   {
      entity.setSituacao(Situacao.E);

      getEntityManager().merge(entity);
   }

   protected void physicalRemove(E entity)
   {
      getEntityManager().remove(entity);
   }

   @Override
   public void inative(E entity)
   {
      entity.setSituacao(Situacao.I);
      getEntityManager().merge(entity);
   }

   /*
    * (non-Javadoc)
    * 
    * @see br.jus.tjrs.cronos.core.model.repository.EntityRepository#findAll()
    */
   @Override
   public List<E> findAll() throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<E> query = builder.createQuery(getEntityType());

      Root<E> from = query.from(getEntityType());

      query.select(from).where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A));

      return createQuery(query).getResultList();
   }

   /*
    * (non-Javadoc)
    * 
    * @see br.jus.tjrs.cronos.model.repository.EntityRepository#find(br.jus.tjrs.cronos.commons.search.SearchConfig)
    */
   @Override
   public PagedResult<E> find(Pagination<E> config)
   {
      CriteriaQuery<E> query = criteriaBuilder().createQuery(getEntityType());

      query.select(query.from(getEntityType()));

      return getPagedResult(config, createQuery(query));
   }

   @Override
   public List<E> findAll(Specification<E> specification) throws CronosException
   {
      CriteriaQuery<E> query = criteriaBuilder().createQuery(getEntityType());

      applySpecificationToCriteria(specification, query);

      return createQuery(query).getResultList();
   }

   @Override
   public PagedResult<E> find(Specification<E> spec, Pagination<E> config)
   {
      return getPagedResult(createQuery(spec), config, spec);
   }

   protected <T> PagedResult<T> getPagedResult(Pagination<T> config, TypedQuery<T> query)
   {
      query.setFirstResult(config.getOffset());
      query.setMaxResults(config.getLimit());

      return new PagedResult<>(config, config.getLimit(), query.getResultList());
   }

   protected <T> PagedResult<T> getPagedResult(TypedQuery<T> query, Pagination<T> config, Specification<E> spec)
   {
      query.setFirstResult(config.getOffset());
      query.setMaxResults(config.getLimit());

      Long total = getResultCount(createCountQuery(spec));
      List<T> result = Collections.<T> emptyList();

      if (total > config.getOffset())
      {
         result = query.getResultList();
      }
      return new PagedResult<>(config, total, result);
   }

   protected TypedQuery<E> createQuery(Specification<E> spec)
   {
      CriteriaQuery<E> criteriaQuery = criteriaBuilder().createQuery(getEntityType());

      Root<E> root = applySpecificationToCriteria(spec, criteriaQuery);

      criteriaQuery.select(root);
      // TODO
      // if (sort != null) {
      // query.orderBy(toOrders(sort, root, builder));
      // }
      return createQuery(criteriaQuery);
   }

   protected TypedQuery<Long> createCountQuery(Specification<E> spec)
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

      Root<E> root = applySpecificationToCriteria(spec, criteriaQuery);
      if (criteriaQuery.isDistinct())
      {
         criteriaQuery.select(builder.countDistinct(root));
      }
      else
      {
         criteriaQuery.select(builder.count(root));
      }
      return createQuery(criteriaQuery);
   }

   protected CriteriaBuilder criteriaBuilder()
   {
      return getEntityManager().getCriteriaBuilder();
   }

   protected <T> TypedQuery<T> createQuery(CriteriaQuery<T> query)
   {
      return getEntityManager().createQuery(query);
   }

   private Long getResultCount(TypedQuery<Long> query)
   {
      long total = 0L;
      for (Long element : query.getResultList())
      {
         total += element == null ? 0 : element;
      }
      return total;
   }

   private <S> Root<E> applySpecificationToCriteria(Specification<E> specification, CriteriaQuery<S> query)
   {
      Root<E> root = query.from(getEntityType());
      if (specification == null)
      {
         return root;
      }
      Predicate predicate = specification.toPredicate(root, query, criteriaBuilder());
      if (predicate != null)
      {
         query.where(predicate);
      }
      return root;
   }
}
