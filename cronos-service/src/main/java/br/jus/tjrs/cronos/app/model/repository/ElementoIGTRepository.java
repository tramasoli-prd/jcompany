package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class ElementoIGTRepository extends AbstractCronosRepository<Long, ElementoIGTEntity>
{
   @Override
   public Class<ElementoIGTEntity> getEntityType()
   {
      return ElementoIGTEntity.class;
   }

   public List<ElementoIGTEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<ElementoIGTEntity> query = builder.createQuery(ElementoIGTEntity.class);

      Root<ElementoIGTEntity> from = query.from(ElementoIGTEntity.class);

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_CATEGORIA), idCategoria),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ORDEM)));
      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<ElementoIGTEntity> listByPartDescription(String part) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<ElementoIGTEntity> query = builder.createQuery(ElementoIGTEntity.class);
      Root<ElementoIGTEntity> from = query.from(ElementoIGTEntity.class);

      query.select(from)
               .where(builder.like(from.<String> get(ConstantUtil.QUERY_PARAM_TEXTO), ConstantUtil.PERCENT + part
                        + ConstantUtil.PERCENT),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A));
      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

}
