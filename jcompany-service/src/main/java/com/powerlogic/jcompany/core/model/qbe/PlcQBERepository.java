/*
 * Copyright 2015 JAXIO http://www.jaxio.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.powerlogic.jcompany.core.model.qbe;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;
import com.powerlogic.jcompany.core.model.entity.IPlcVersionedEntity;
import com.powerlogic.jcompany.core.model.repository.IPlcEntityRepository;

/**
 * 
 * Query by Example
 * 
 * 
 * JPA 2 {@link PlcQBERepository} implementation
 */
public abstract class PlcQBERepository<PK extends Serializable, E extends IPlcEntityModel<PK>> implements IPlcEntityRepository<PK, E> {
	
    @Inject
    protected ByExampleUtil byExampleUtil;
    
    @Inject
    protected ByPatternUtil byPatternUtil;
    
    @Inject
    protected ByRangeUtil byRangeUtil;
    
    @Inject
    protected ByNamedQueryUtil byNamedQueryUtil;
    
    @Inject
    protected ByPropertySelectorUtil byPropertySelectorUtil;
    
    @Inject
    protected OrderByUtil orderByUtil;
    
    @Inject
    protected MetamodelUtil metamodelUtil;
    
    @Inject
    private JpaUtil jpaUtil;
    
    @Inject
    protected ByFullTextUtil byFullTextUtil;
    
    protected List<SingularAttribute<?, ?>> indexedAttributes;

    protected Logger log;

    /**
     * This constructor needs the real type of the generic type E so it can be given to the {@link javax.persistence.EntityManager}.
     */
    public PlcQBERepository() {
        this.log = LoggerFactory.getLogger(getClass());
    }

    @PostConstruct
    protected void init() {
        this.indexedAttributes = buildIndexedAttributes(getEntityType());
    }


    /**
     * Create a new instance of the repository type.
     *
     * @return a new instance with no property set.
     */
    public E getNew() {
    	try {
			return getEntityType().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create();
		}
    }

    /**
     * Creates a new instance and initializes it with some default values.
     *
     * @return a new instance initialized with default values.
     */
    public E getNewWithDefaults() {
        return getNew();
    }

    /**
     * Gets from the repository the E entity instance.
     * <p>
     * DAO for the local database will typically use the primary key or unique fields of the given entity, while DAO for external repository may use a unique
     * field present in the entity as they probably have no knowledge of the primary key. Hence, passing the entity as an argument instead of the primary key
     * allows you to switch the DAO more easily.
     *
     * @param entity an E instance having a primary key set
     * @return the corresponding E persistent instance or null if none could be found.
     */
    public E get(E entity) {
        return entity == null ? null : getById(entity.getId());
    }

	/** 
	 * Abstract Method forçando a implementação na classe concreta.
	 * 
	 * Deve ser realizada uma injeção da EntityManager default.
	 *  
	 */
    protected abstract EntityManager getEntityManager();
    
    public abstract Class<E> getEntityType();
    
    public E getById(PK pk) {
        if (pk == null) {
            return null;
        }

        E entityFound = getEntityManager().find(getEntityType(), pk);
        if (entityFound == null) {
            log.warn("get returned null with id={}", pk);
        }
        return entityFound;
    }

    /**
     * Refresh the given entity with up to date data. Does nothing if the given entity is a new entity (not yet managed).
     *
     * @param entity the entity to refresh.
     */
    public void refresh(E entity) {
        if (getEntityManager().contains(entity)) {
            getEntityManager().refresh(entity);
        }
    }

    /**
     * Find and load all instances.
     */
    public List<E> find() {
    	SearchParameters sp = new SearchParameters();
    	sp.caseInsensitive().startingLike();    	
        return find(getNew(), new SearchParameters());
    }

    /**
     * Find and load a list of E instance.
     *
     * @param entity a sample entity whose non-null properties may be used as search hints
     * @return the entities matching the search.
     */
    public List<E> find(E entity) {
    	SearchParameters sp = new SearchParameters();
    	sp.caseInsensitive().startingLike();
        return find(entity, sp);
    }

    /**
     * Find and load a list of E instance.
     *
     * @param searchParameters carries additional search information
     * @return the entities matching the search.
     */
    public List<E> find(SearchParameters searchParameters) {
        return find(getNew(), searchParameters);
    }

    /**
     * Find and load a list of E instance.
     *
     * @param entity a sample entity whose non-null properties may be used as search hints
     * @param sp     carries additional search information
     * @return the entities matching the search.
     */
    public List<E> find(E entity, SearchParameters sp) {
    	
    	if (IPlcVersionedEntity.class.isAssignableFrom(entity.getClass())) {
    		((IPlcVersionedEntity)entity).setVersao(null);
    		((IPlcVersionedEntity)entity).setSituacao(PlcSituacao.A);
    	}
   	
        if (sp.hasNamedQuery()) {
            return byNamedQueryUtil.findByNamedQuery(sp);
        }
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = builder.createQuery(getEntityType());
        if (sp.getDistinct()) {
            criteriaQuery.distinct(true);
        }
        Root<E> root = criteriaQuery.from(getEntityType());

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, entity, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // fetches
        fetches(sp, root);

        // order by
        criteriaQuery.orderBy(orderByUtil.buildJpaOrders(sp.getOrders(), root, builder, sp));

        TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);
        jpaUtil.applyPagination(typedQuery, sp);
        List<E> entities = typedQuery.getResultList();
        log.debug("Returned {} elements", entities.size());

        return entities;
    }

    /**
     * Find a list of E property.
     *
     * @param propertyType type of the property
     * @param entity       a sample entity whose non-null properties may be used as search hints
     * @param sp           carries additional search information
     * @param path         the path to the property
     * @return the entities property matching the search.
     */
    public <T> List<T> findProperty(Class<T> propertyType, E entity, SearchParameters sp, String path) {
        return findProperty(propertyType, entity, sp, metamodelUtil.toAttributes(path, getEntityType()));
    }

    /**
     * Find a list of E property.
     *
     * @param propertyType type of the property
     * @param entity       a sample entity whose non-null properties may be used as search hints
     * @param sp           carries additional search information
     * @param attributes   the list of attributes to the property
     * @return the entities property matching the search.
     */
    public <T> List<T> findProperty(Class<T> propertyType, E entity, SearchParameters sp, Attribute<?, ?>... attributes) {
        return findProperty(propertyType, entity, sp, newArrayList(attributes));
    }

    /**
     * Find a list of E property.
     *
     * @param propertyType type of the property
     * @param entity       a sample entity whose non-null properties may be used as search hints
     * @param sp           carries additional search information
     * @param attributes   the list of attributes to the property
     * @return the entities property matching the search.
     */
    public <T> List<T> findProperty(Class<T> propertyType, E entity, SearchParameters sp, List<Attribute<?, ?>> attributes) {
        if (sp.hasNamedQuery()) {
            return byNamedQueryUtil.findByNamedQuery(sp);
        }
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(propertyType);
        if (sp.getDistinct()) {
            criteriaQuery.distinct(true);
        }
        Root<E> root = criteriaQuery.from(getEntityType());
        Path<T> path = jpaUtil.getPath(root, attributes);
        criteriaQuery.select(path);

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, entity, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // fetches
        fetches(sp, root);

        // order by
        // we do not want to follow order by specified in search parameters
        criteriaQuery.orderBy(builder.asc(path));

        TypedQuery<T> typedQuery = getEntityManager().createQuery(criteriaQuery);
        jpaUtil.applyPagination(typedQuery, sp);
        List<T> entities = typedQuery.getResultList();
        log.debug("Returned {} elements", entities.size());

        return entities;
    }

    /**
     * Count the number of E instances.
     *
     * @param sp carries additional search information
     * @return the number of entities matching the search.
     */
    public int findCount(SearchParameters sp) {
        return findCount(getNew(), sp);
    }

    /**
     * Count the number of E instances.
     *
     * @param entity a sample entity whose non-null properties may be used as search hint
     * @return the number of entities matching the search.
     */
    public int findCount(E entity) {
        return findCount(entity, new SearchParameters());
    }

    /**
     * Count the number of E instances.
     *
     * @param entity a sample entity whose non-null properties may be used as search hint
     * @param sp     carries additional search information
     * @return the number of entities matching the search.
     */
    public int findCount(E entity, SearchParameters sp) {
        checkNotNull(entity, "The entity cannot be null");
        checkNotNull(sp, "The searchParameters cannot be null");

        if (sp.hasNamedQuery()) {
            return byNamedQueryUtil.numberByNamedQuery(sp).intValue();
        }
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(getEntityType());

        if (sp.getDistinct()) {
            criteriaQuery = criteriaQuery.select(builder.countDistinct(root));
        } else {
            criteriaQuery = criteriaQuery.select(builder.count(root));
        }

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, entity, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // construct order by to fetch or joins if needed
        orderByUtil.buildJpaOrders(sp.getOrders(), root, builder, sp);

        TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);

        return typedQuery.getSingleResult().intValue();
    }

    /**
     * Count the number of E instances.
     *
     * @param entity a sample entity whose non-null properties may be used as search hint
     * @param sp     carries additional search information
     * @param path   the path to the property
     * @return the number of entities matching the search.
     */
    public int findPropertyCount(E entity, SearchParameters sp, String path) {
        return findPropertyCount(entity, sp, metamodelUtil.toAttributes(path, getEntityType()));
    }

    /**
     * Count the number of E instances.
     *
     * @param entity     a sample entity whose non-null properties may be used as search hint
     * @param sp         carries additional search information
     * @param attributes the list of attributes to the property
     * @return the number of entities matching the search.
     */
    public int findPropertyCount(E entity, SearchParameters sp, Attribute<?, ?>... attributes) {
        return findPropertyCount(entity, sp, newArrayList(attributes));
    }

    /**
     * Count the number of E instances.
     *
     * @param entity     a sample entity whose non-null properties may be used as search hint
     * @param sp         carries additional search information
     * @param attributes the list of attributes to the property
     * @return the number of entities matching the search.
     */
    public int findPropertyCount(E entity, SearchParameters sp, List<Attribute<?, ?>> attributes) {
        if (sp.hasNamedQuery()) {
            return byNamedQueryUtil.numberByNamedQuery(sp).intValue();
        }
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(getEntityType());
        Path<?> path = jpaUtil.getPath(root, attributes);

        if (sp.getDistinct()) {
            criteriaQuery = criteriaQuery.select(builder.countDistinct(path));
        } else {
            criteriaQuery = criteriaQuery.select(builder.count(path));
        }

        // predicate
        Predicate predicate = getPredicate(criteriaQuery, root, builder, entity, sp);
        if (predicate != null) {
            criteriaQuery = criteriaQuery.where(predicate);
        }

        // construct order by to fetch or joins if needed
        orderByUtil.buildJpaOrders(sp.getOrders(), root, builder, sp);

        TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);

        return typedQuery.getSingleResult().intValue();
    }

    
    public E findUnique(SearchParameters sp) {
        return findUnique(getNew(), sp);
    }

    public E findUnique(E e) {
        return findUnique(e, new SearchParameters());
    }
    
    public E findUnique(E entity, SearchParameters sp) {
        E result = findUniqueOrNone(entity, sp);
        if (result != null) {
            return result;
        }
        throw new NoResultException("Developper: You expected 1 result but found none !");
    }
    
    public E findUniqueOrNone(SearchParameters sp) {
        return findUniqueOrNone(getNew(), sp);
    }
    
    public E findUniqueOrNone(E entity) {
        return findUniqueOrNone(entity, new SearchParameters());
    }

    /**
     * We request at most 2, if there's more than one then we throw a {@link javax.persistence.NonUniqueResultException}
     *
     * @throws javax.persistence.NonUniqueResultException
     */
    public E findUniqueOrNone(E entity, SearchParameters sp) {
        // this code is an optimization to prevent using a count
        sp.setFirst(0);
        sp.setMaxResults(2);
        List<E> results = find(entity, sp);

        if (results == null || results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new NonUniqueResultException("Developper: You expected 1 result but we found more ! sample: " + entity);
        } else {
            return results.iterator().next();
        }
    }
    
    public E findUniqueOrNew(SearchParameters sp) {
        return findUniqueOrNew(getNew(), sp);
    }
    
    public E findUniqueOrNew(E e) {
        return findUniqueOrNew(e, new SearchParameters());
    }

    public E findUniqueOrNew(E entity, SearchParameters sp) {
        E result = findUniqueOrNone(entity, sp);
        if (result != null) {
            return result;
        } else {
            return getNewWithDefaults();
        }
    }

    protected <R> Predicate getPredicate(CriteriaQuery<?> criteriaQuery, Root<E> root, CriteriaBuilder builder, E entity, SearchParameters sp) {
        return jpaUtil.andPredicate(builder, // 
                bySearchPredicate(root, builder, entity, sp), //
                byMandatoryPredicate(criteriaQuery, root, builder, entity, sp));
    }

    protected <R> Predicate bySearchPredicate(Root<E> root, CriteriaBuilder builder, E entity, SearchParameters sp) {
        return jpaUtil.concatPredicate(sp, builder, //
                byRanges(root, builder, sp, getEntityType()), //
                byPropertySelectors(root, builder, sp), //
                byExample(root, builder, sp, entity), //
                byPattern(root, builder, sp, getEntityType()));
    }


    protected Predicate byExample(Root<E> root, CriteriaBuilder builder, SearchParameters sp, E entity) {
        return byExampleUtil.byExampleOnEntity(root, entity, builder, sp);
    }

    protected Predicate byPropertySelectors(Root<E> root, CriteriaBuilder builder, SearchParameters sp) {
        return byPropertySelectorUtil.byPropertySelectors(root, builder, sp);
    }

    protected Predicate byRanges(Root<E> root, CriteriaBuilder builder, SearchParameters sp, Class<E> type) {
        return byRangeUtil.byRanges(root, builder, sp, type);
    }

    protected Predicate byPattern(Root<E> root, CriteriaBuilder builder, SearchParameters sp, Class<E> type) {
        return byPatternUtil.byPattern(root, builder, sp, type);
    }

    /**
     * You may override this method to add a Predicate to the default find method.
     */
    protected <R> Predicate byMandatoryPredicate(CriteriaQuery<?> criteriaQuery, Root<E> root, CriteriaBuilder builder, E entity, SearchParameters sp) {
        return null;
    }

    /**
     * Save or update the given entity E to the repository. Assume that the entity is already present in the persistence context. No merge is done.
     *
     * @param entity the entity to be saved or updated.
     */
    public E save(E entity) {
        checkNotNull(entity, "The entity to save cannot be null");

        // creation with auto generated id
        if (!entity.isIdSet()) {
            getEntityManager().persist(entity);
            return entity;
        }

        // creation with manually assigned key
        if (jpaUtil.isEntityIdManuallyAssigned(getEntityType()) && !getEntityManager().contains(entity)) {
            getEntityManager().persist(entity);
            return entity;
        }
        // other cases are update
        // the simple fact to invoke this method, from a service method annotated with ,
        // does the job (assuming the give entity is present in the persistence context)
        return null;
    }

    /**
     * Persist the given entity.
     */
    public void persist(E entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Merge the state of the given entity into the current persistence context.
     */
    public E merge(E entity) {
        return getEntityManager().merge(entity);
    }

    /**
     * Delete the given entity E from the repository.
     *
     * @param entity the entity to be deleted.
     */
    public void delete(E entity) {
        if (getEntityManager().contains(entity)) {
            getEntityManager().remove(entity);
        } else {
            // could be a delete on a transient instance
            E entityRef = getEntityManager().getReference(getEntityType(), entity.getId());

            if (entityRef != null) {
                getEntityManager().remove(entityRef);
            } else {
                log.warn("Attempt to delete an instance that is not present in the database: {}", entity);
            }
        }
    }

    protected List<SingularAttribute<?, ?>> buildIndexedAttributes(Class<E> type) {
        List<SingularAttribute<?, ?>> ret = newArrayList();
        for (Method m : type.getMethods()) {
        	// TODO Powerlogic/Jaguar
        	//      if (m.getAnnotation(Field.class) != null) {
        	//          ret.add(metamodelUtil.toAttribute(jpaUtil.methodToProperty(m), type));
        	//      }
        }
        return ret;
    }

    public boolean isIndexed(String property) {
        return !property.contains(".") && indexedAttributes.contains(metamodelUtil.toAttribute(property, getEntityType()));
    }

    // -----------------
    // Util
    // -----------------

    /**
     * Helper to determine if the passed given property is null. Used mainly on binary lazy loaded property.
     *
     * @param id       the entity id
     * @param property the property to check
     */
    public boolean isPropertyNull(PK id, SingularAttribute<E, ?> property) {
        checkNotNull(id, "The id cannot be null");
        checkNotNull(property, "The property cannot be null");
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(getEntityType());
        criteriaQuery = criteriaQuery.select(builder.count(root));

        // predicate
        Predicate idPredicate = builder.equal(root.get("id"), id);
        Predicate isNullPredicate = builder.isNull(root.get(property));
        criteriaQuery = criteriaQuery.where(jpaUtil.andPredicate(builder, idPredicate, isNullPredicate));

        TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);
        return typedQuery.getSingleResult().intValue() == 1;
    }

    /**
     * Return the optimistic version value, if any.
     */
    @SuppressWarnings("unchecked")
    public Comparable<Object> getVersion(E entity) {
        EntityType<E> entityType = getEntityManager().getMetamodel().entity(getEntityType());
        if (!entityType.hasVersionAttribute()) {
            return null;
        }
        return (Comparable<Object>) jpaUtil.getValue(entity, getVersionAttribute(entityType));
    }

    /**
     * _HACK_ too bad that JPA does not provide this entityType.getVersion();
     * <p>
     * http://stackoverflow.com/questions/13265094/generic-way-to-get-jpa-entity-version
     */
    protected SingularAttribute<? super E, ?> getVersionAttribute(EntityType<E> entityType) {
        for (SingularAttribute<? super E, ?> sa : entityType.getSingularAttributes()) {
            if (sa.isVersion()) {
                return sa;
            }
        }
        return null;
    }

    // -----------------
    // Commons
    // -----------------


    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void fetches(SearchParameters sp, Root<E> root) {
        for (List<Attribute<?, ?>> args : sp.getFetches()) {
            FetchParent<?, ?> from = root;
            for (Attribute<?, ?> arg : args) {
                boolean found = false;
                for (Fetch<?, ?> fetch : from.getFetches()) {
                    if (arg.equals(fetch.getAttribute())) {
                        from = fetch;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (arg instanceof PluralAttribute) {
                        from = from.fetch((PluralAttribute) arg, JoinType.LEFT);
                    } else {
                        from = from.fetch((SingularAttribute) arg, JoinType.LEFT);
                    }
                }
            }
        }
    }
}