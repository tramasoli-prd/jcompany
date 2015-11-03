package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaSentencaEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TaxonomiaSentencaRepository extends AbstractCronosRepository<Long, TaxonomiaSentencaEntity>
{
   @Override
   public Class<TaxonomiaSentencaEntity> getEntityType()
   {
      return TaxonomiaSentencaEntity.class;
   }

   public List<TaxonomiaSentencaEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaSentencaEntity> query = builder.createQuery(TaxonomiaSentencaEntity.class);

      Root<TaxonomiaSentencaEntity> from = query.from(TaxonomiaSentencaEntity.class);

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

   public List<TaxonomiaSentencaEntity> findAllBySentenca(Long idSentenca) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaSentencaEntity> query = builder.createQuery(TaxonomiaSentencaEntity.class);

      Root<TaxonomiaSentencaEntity> from = query.from(TaxonomiaSentencaEntity.class);

      query.select(from)
               .where(builder.equal(from.get("idSentenca"), idSentenca),
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
