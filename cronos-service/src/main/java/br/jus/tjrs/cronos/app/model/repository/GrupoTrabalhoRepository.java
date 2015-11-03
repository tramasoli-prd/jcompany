package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.commons.search.Pagination;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class GrupoTrabalhoRepository extends AbstractCronosRepository<Long, GrupoTrabalhoEntity>
{
   @Override
   public Class<GrupoTrabalhoEntity> getEntityType()
   {
      return GrupoTrabalhoEntity.class;
   }

   public GrupoTrabalhoEntity findByCpf(Pagination<GrupoTrabalhoEntity> config, String cpf)
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<GrupoTrabalhoEntity> query = builder.createQuery(GrupoTrabalhoEntity.class);

      Root<GrupoTrabalhoEntity> from = query.from(GrupoTrabalhoEntity.class);

      query.select(from)
               .where(builder.equal(from.get("cpf"), cpf),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A));

      GrupoTrabalhoEntity grupoTrabalho;
      try
      {
         grupoTrabalho = createQuery(query).getSingleResult();
      }
      catch (NoResultException e)
      {
         grupoTrabalho = null;
      }
      return grupoTrabalho;
   }

}
