package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.AlegacaoGTEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class AlegacaoGTRepository extends AbstractCronosRepository<Long, AlegacaoGTEntity>
{
   @Override
   public Class<AlegacaoGTEntity> getEntityType()
   {
      return AlegacaoGTEntity.class;
   }

   public List<AlegacaoGTEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<AlegacaoGTEntity> query = builder.createQuery(AlegacaoGTEntity.class);

      Root<AlegacaoGTEntity> from = query.from(AlegacaoGTEntity.class);

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get("textoAlegacao")));

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
