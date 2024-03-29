package com.powerlogic.jcompany.core.model.qbe;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.powerlogic.jcompany.core.model.qbe.OrderByDirection.ASC;
import static com.powerlogic.jcompany.core.model.qbe.OrderByDirection.DESC;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.Attribute;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Holder class for search ordering used by the {@link SearchParameters}.
 */
public class OrderBy implements Serializable {
    private static final long serialVersionUID = 1L;
    private final PathHolder pathHolder;
    private OrderByDirection direction = ASC;

    public OrderBy(OrderByDirection direction, Attribute<?, ?>... attributes) {
        this.direction = checkNotNull(direction);
        this.pathHolder = new PathHolder(checkNotNull(attributes));
    }

    public OrderBy(OrderByDirection direction, String path, Class<?> from) {
        this.direction = checkNotNull(direction);
        this.pathHolder = new PathHolder(checkNotNull(path), checkNotNull(from));
    }

    public List<Attribute<?, ?>> getAttributes() {
        return pathHolder.getAttributes();
    }

    public String getPath() {
        return pathHolder.getPath();
    }

    public OrderByDirection getDirection() {
        return direction;
    }

    public boolean isOrderDesc() {
        return DESC == direction;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pathHolder == null) ? 0 : pathHolder.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        OrderBy other = (OrderBy) obj;
        if (pathHolder == null) {
            if (other.pathHolder != null) {
                return false;
            }
        } else if (!pathHolder.equals(other.pathHolder)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}