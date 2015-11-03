package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TaxonomiaEtiquetaGTRepository extends AbstractCronosRepository<Long, TaxonomiaEtiquetaGTEntity>
{
   @Override
   public Class<TaxonomiaEtiquetaGTEntity> getEntityType()
   {
      return TaxonomiaEtiquetaGTEntity.class;
   }

   public List<TaxonomiaEtiquetaGTEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaEtiquetaGTEntity> query = builder.createQuery(TaxonomiaEtiquetaGTEntity.class);

      Root<TaxonomiaEtiquetaGTEntity> from = query.from(TaxonomiaEtiquetaGTEntity.class);

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

   public List<TaxonomiaEtiquetaGTEntity> findAllByEtiqueta(Long idEtiqueta) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaEtiquetaGTEntity> query = builder.createQuery(TaxonomiaEtiquetaGTEntity.class);

      Root<TaxonomiaEtiquetaGTEntity> from = query.from(TaxonomiaEtiquetaGTEntity.class);

      query.select(from)
               .where(builder.equal(from.get("idEtiquetaGT"), idEtiqueta),
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
}
