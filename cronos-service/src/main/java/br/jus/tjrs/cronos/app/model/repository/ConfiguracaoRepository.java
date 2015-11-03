package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.ConfiguracaoEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class ConfiguracaoRepository extends AbstractCronosRepository<Long, ConfiguracaoEntity>
{
   @Override
   public Class<ConfiguracaoEntity> getEntityType()
   {
      return ConfiguracaoEntity.class;
   }

   public ConfiguracaoEntity buscaConfiguracaoByGRupoTrabalho(Long id) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<ConfiguracaoEntity> query = builder.createQuery(ConfiguracaoEntity.class);

      Root<ConfiguracaoEntity> from = query.from(ConfiguracaoEntity.class);

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), id),
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
}
