package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.SentencaEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class SentencaRepository extends AbstractCronosRepository<Long, SentencaEntity>
{
   @Override
   public Class<SentencaEntity> getEntityType()
   {
      return SentencaEntity.class;
   }

   public SentencaEntity findByNumeroProcessoFinalizada(String numeroProcesso, SimNao finalizada)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<SentencaEntity> query = builder.createQuery(SentencaEntity.class);
      Root<SentencaEntity> from = query.from(SentencaEntity.class);
      query.select(from)
               .where(builder.equal(from.get("numeroProcesso"), numeroProcesso),
                        builder.equal(from.get("finalizada"), finalizada),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ID)));

      try
      {
         return createQuery(query).getSingleResult();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<SentencaEntity> listByGrupoTrabalhoFinalizada(Long idGrupoTrabalho, SimNao finalizada)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<SentencaEntity> query = builder.createQuery(SentencaEntity.class);
      Root<SentencaEntity> from = query.from(SentencaEntity.class);
      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.equal(from.get("finalizada"), finalizada),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ID)));

      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public Integer maxOrderByFinalizada(SimNao finalizada) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<Integer> query = builder.createQuery(Integer.class);

      Root<SentencaEntity> from = query.from(SentencaEntity.class);

      query.multiselect(builder.max(from.<Number> get(ConstantUtil.QUERY_PARAM_ORDEM))).where(
               builder.equal(from.get("finalizada"), finalizada));

      try
      {
         return createQuery(query).getSingleResult();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }
}
