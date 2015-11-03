package br.jus.tjrs.cronos.core.model.service;

import br.jus.tjrs.cronos.core.model.entity.EntityModel;
import br.jus.tjrs.cronos.core.model.repository.specification.Specification;

public interface Argument<E extends EntityModel<?>>
{
   Specification<E> toSpecification();
}
