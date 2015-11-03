package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.EtiquetaGTEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class EtiquetaGTRepository extends AbstractCronosRepository<Long, EtiquetaGTEntity>
{
   @Override
   public Class<EtiquetaGTEntity> getEntityType()
   {
      return EtiquetaGTEntity.class;
   }

   public List<EtiquetaGTEntity> listByPartDescription(String part, Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<EtiquetaGTEntity> query = builder.createQuery(EtiquetaGTEntity.class);

      Root<EtiquetaGTEntity> from = query.from(EtiquetaGTEntity.class);

      query.select(from)
               .where(builder.like(builder.upper(from.<String> get(ConstantUtil.QUERY_PARAM_ETIQUETA)),
                        ConstantUtil.PERCENT + part.toUpperCase() + ConstantUtil.PERCENT),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ETIQUETA)));
      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public EtiquetaGTEntity findEtiquetaGrupoTrabalho(String etiqueta, Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<EtiquetaGTEntity> query = builder.createQuery(EtiquetaGTEntity.class);

      Root<EtiquetaGTEntity> from = query.from(EtiquetaGTEntity.class);

      query.select(from)
               .where(builder.like(from.<String> get(ConstantUtil.QUERY_PARAM_ETIQUETA), etiqueta),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ETIQUETA)));
      try
      {
         List<EtiquetaGTEntity> resultList = createQuery(query).getResultList();
         if (resultList != null && !resultList.isEmpty())
         {
            resultList.get(0);
         }

      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
      return null;
   }
}
