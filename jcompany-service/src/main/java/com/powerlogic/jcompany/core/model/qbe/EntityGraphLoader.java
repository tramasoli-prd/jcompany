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

import java.io.Serializable;
import java.util.Collection;

import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;

/**
 * The EntityGraphLoader is used to load within a single read-only transaction all the desired associations that
 * are normally lazily loaded.
 */
public abstract class EntityGraphLoader<T extends IPlcEntityModel<PK>, PK extends Serializable> {

    protected PlcQBERepository<PK, T> repository;

    // required by cglib to create a proxy around the object as we are using the  annotation
    public EntityGraphLoader() {
    }

    public EntityGraphLoader(PlcQBERepository<PK, T> repository) {
        this.repository = repository;
    }

    /*
     * Get the entity by id and load its graph using loadGraph.
     */
    
    public T getById(PK pk) {
        T entity = repository.getById(pk);
        loadGraph(entity);
        return entity;
    }

    /*
     * Merge the passed entity and load the graph of the merged entity using loadGraph.
     */
    
    public T merge(T entity) {
        T mergedEntity = repository.merge(entity);
        loadGraph(mergedEntity);
        return mergedEntity;
    }

    /*
     * Load whatever is needed in the graph of the passed entity, for example x-to-many collection, x-to-one object, etc.
     */
    public abstract void loadGraph(T entity);

    /*
     * Load the passed 'x-to-many' association.
     */
    protected void loadCollection(Collection<?> collection) {
        if (collection != null) {
            collection.size();
        }
    }

    /*
     * Load the passed 'x-to-one' association.
     */
    protected void loadSingular(Object association) {
        if (association != null) {
            association.toString();
        }
    }
}