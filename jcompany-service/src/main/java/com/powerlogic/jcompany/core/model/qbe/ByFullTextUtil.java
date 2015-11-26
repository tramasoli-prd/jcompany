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

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;

import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/**
 * @author Nicolas Romanetti
 * @author Florent Ramière
 * @author Sébastien Péralta
 * @author Jean-Louis Boudart
 */
@Named
@Singleton
public class ByFullTextUtil {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private JpaUtil jpaUtil;


    private boolean hasNonEmptyTerms(SearchParameters sp) {
        for (TermSelector termSelector : sp.getTerms()) {
            if (termSelector.isNotEmpty()) {
                return true;
            }
        }
        return false;
    }



    public <T extends IPlcEntityModel<?>> Predicate byExampleOnEntity(Root<T> rootPath, T entityValue, SearchParameters sp, CriteriaBuilder builder) {
        if (entityValue == null) {
            return null;
        }

        Class<T> type = rootPath.getModel().getBindableJavaType();
        ManagedType<T> mt = em.getMetamodel().entity(type);

        List<Predicate> predicates = newArrayList();
        predicates.addAll(byExample(mt, rootPath, entityValue, sp, builder));
        predicates.addAll(byExampleOnCompositePk(rootPath, entityValue, sp, builder));
        return jpaUtil.orPredicate(builder, predicates);
    }

    protected <T extends IPlcEntityModel<?>> List<Predicate> byExampleOnCompositePk(Root<T> root, T entity, SearchParameters sp, CriteriaBuilder builder) {
        String compositePropertyName = jpaUtil.compositePkPropertyName(entity);
        if (compositePropertyName == null) {
            return emptyList();
        } else {
            return newArrayList(byExampleOnEmbeddable(root.get(compositePropertyName), entity.getId(), sp, builder));
        }
    }

    public <E> Predicate byExampleOnEmbeddable(Path<E> embeddablePath, E embeddableValue, SearchParameters sp, CriteriaBuilder builder) {
        if (embeddableValue == null) {
            return null;
        }

        Class<E> type = embeddablePath.getModel().getBindableJavaType();
        ManagedType<E> mt = em.getMetamodel().embeddable(type); // note: calling .managedType() does not work
        return jpaUtil.orPredicate(builder, byExample(mt, embeddablePath, embeddableValue, sp, builder));
    }

    /*
     * Add a predicate for each simple property whose value is not null.
     */
    public <T> List<Predicate> byExample(ManagedType<T> mt, Path<T> mtPath, T mtValue, SearchParameters sp, CriteriaBuilder builder) {
        List<Predicate> predicates = newArrayList();
        for (SingularAttribute<? super T, ?> attr : mt.getSingularAttributes()) {
            if (!isPrimaryKey(mt, attr)) {
                continue;
            }

            Object attrValue = jpaUtil.getValue(mtValue, attr);
            if (attrValue != null) {
                predicates.add(builder.equal(mtPath.get(jpaUtil.attribute(mt, attr)), attrValue));
            }
        }
        return predicates;
    }

    private <T> boolean isPrimaryKey(ManagedType<T> mt, SingularAttribute<? super T, ?> attr) {
        if (mt.getJavaType().getAnnotation(Embeddable.class) != null) {
            return true;
        }
        return jpaUtil.isPk(mt, attr);
    }
}