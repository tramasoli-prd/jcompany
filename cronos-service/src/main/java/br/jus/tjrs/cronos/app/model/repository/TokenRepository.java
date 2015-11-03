package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.TokenEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TokenRepository extends AbstractCronosRepository<Long, TokenEntity>
{
   @Override
   public Class<TokenEntity> getEntityType()
   {
      return TokenEntity.class;
   }

   @Override
   public List<TokenEntity> findAll() throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TokenEntity> query = builder.createQuery(getEntityType());

      Root<TokenEntity> from = query.from(getEntityType());

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A))
               .orderBy(builder.asc(from.get("nome")));

      return createQuery(query).getResultList();
   }

}
