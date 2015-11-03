package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaUsuarioEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class TaxonomiaUsuarioRepository extends AbstractCronosRepository<Long, TaxonomiaUsuarioEntity>
{
   @Override
   public Class<TaxonomiaUsuarioEntity> getEntityType()
   {
      return TaxonomiaUsuarioEntity.class;
   }

   public List<TaxonomiaUsuarioEntity> findAllByUsuario(Long idUsuario) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaUsuarioEntity> query = builder.createQuery(TaxonomiaUsuarioEntity.class);

      Root<TaxonomiaUsuarioEntity> from = query.from(TaxonomiaUsuarioEntity.class);

      query.select(from)
               .where(builder.equal(from.get("idUsuario"), idUsuario),
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

   public List<TaxonomiaUsuarioEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<TaxonomiaUsuarioEntity> query = builder.createQuery(TaxonomiaUsuarioEntity.class);
      Root<TaxonomiaUsuarioEntity> from = query.from(TaxonomiaUsuarioEntity.class);
      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
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
   
   public List<TaxonomiaUsuarioEntity> findAllByUsuarioGrupoTrabalho(Long idUsuario, Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<TaxonomiaUsuarioEntity> query = builder.createQuery(TaxonomiaUsuarioEntity.class);

      Root<TaxonomiaUsuarioEntity> from = query.from(TaxonomiaUsuarioEntity.class);

      query.select(from)
               .where(builder.equal(from.get("idUsuario"), idUsuario),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
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


   public Integer maxOrderGrupo(Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
      Root<TaxonomiaUsuarioEntity> from = query.from(TaxonomiaUsuarioEntity.class);

      query.multiselect(builder.max(from.<Number> get(ConstantUtil.QUERY_PARAM_ORDEM))).where(
               builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho));
      try
      {
         return createQuery(query).getSingleResult();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public Integer maxOrderUsuario(Long idUsuario) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
      Root<TaxonomiaUsuarioEntity> from = query.from(TaxonomiaUsuarioEntity.class);

      query.multiselect(builder.max(from.<Number> get(ConstantUtil.QUERY_PARAM_ORDEM))).where(
               builder.equal(from.get("idUsuario"), idUsuario));
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
