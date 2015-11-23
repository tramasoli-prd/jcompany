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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Helper to create list of {@link javax.persistence.criteria.Order} out of {@link OrderBy}s.
 */
@Named
@Singleton
public class OrderByUtil {

    @Inject
    private JpaUtil jpaUtil;

    public <E> List<Order> buildJpaOrders(Iterable<OrderBy> orders, Root<E> root, CriteriaBuilder builder, SearchParameters sp) {
        List<Order> jpaOrders = newArrayList();
        for (OrderBy ob : orders) {
            Path<?> path = jpaUtil.getPath(root, ob.getAttributes());
            jpaOrders.add(ob.isOrderDesc() ? builder.desc(path) : builder.asc(path));
        }
        return jpaOrders;
    }
}