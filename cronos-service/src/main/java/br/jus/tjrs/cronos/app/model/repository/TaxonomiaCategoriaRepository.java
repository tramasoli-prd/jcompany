package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaCategoriaEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TaxonomiaCategoriaRepository extends AbstractCronosRepository<Long, TaxonomiaCategoriaEntity>
{
   @Override
   public Class<TaxonomiaCategoriaEntity> getEntityType()
   {
      return TaxonomiaCategoriaEntity.class;
   }

   public List<TaxonomiaCategoriaEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaCategoriaEntity> query = builder.createQuery(TaxonomiaCategoriaEntity.class);

      Root<TaxonomiaCategoriaEntity> from = query.from(TaxonomiaCategoriaEntity.class);

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

   public List<TaxonomiaCategoriaEntity> findAllByModelo(Long idModelo) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaCategoriaEntity> query = builder.createQuery(TaxonomiaCategoriaEntity.class);

      Root<TaxonomiaCategoriaEntity> from = query.from(TaxonomiaCategoriaEntity.class);

      query.select(from)
               .where(builder.equal(from.get("idModeloSentenca"), idModelo),
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
