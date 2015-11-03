package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.domain.TipoNodo;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class CategoriaItemRepository extends AbstractCronosRepository<Long, CategoriaItemEntity>
{
   @Override
   public Class<CategoriaItemEntity> getEntityType()
   {
      return CategoriaItemEntity.class;
   }

   public CategoriaItemEntity findPaiByTipoArvoreGrupoTrabalho(TipoArvore tipoArvore, Long grupoTrabalho)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);

      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.equal(from.get("arvore"), tipoArvore),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), grupoTrabalho),
                        builder.isNull(from.get(ConstantUtil.QUERY_PARAM_ID_PAI)))
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

   public CategoriaItemEntity findByTipoArvoreGrupoTrabalhoTitulo(TipoArvore tipoArvore, Long grupoTrabalho,
            String titulo)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);

      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.equal(from.get("arvore"), tipoArvore),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), grupoTrabalho),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_TITULO), titulo))
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

   public Integer maxOrderByPai(Long idPai) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<Integer> query = builder.createQuery(Integer.class);

      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.multiselect(builder.max(from.<Number> get(ConstantUtil.QUERY_PARAM_ORDEM))).where(
               builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_PAI), idPai));

      try
      {
         return createQuery(query).getSingleResult();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<CategoriaItemEntity> findAllByPai(Long idCategoria) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);

      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_PAI), idCategoria),
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

   public List<CategoriaItemEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);

      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.isNull(from.get(ConstantUtil.QUERY_PARAM_ID_PAI)),
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

   public List<CategoriaItemEntity> listByPartDescription(Long idGrupoTrabalho, String part, TipoNodo tipoNodo,
            TipoArvore arvore)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);
      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.like(builder.upper(from.<String> get(ConstantUtil.QUERY_PARAM_TITULO)),
                        ConstantUtil.PERCENT + part.toUpperCase() + ConstantUtil.PERCENT),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.equal(from.get("arvore"), arvore),
                        builder.equal(from.get("tipo"), tipoNodo),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A)
               )
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_TITULO)));
      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<CategoriaItemEntity> listByPartDescriptionTipoArvore(String part, TipoArvore tipoArvore)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);
      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.like(from.<String> get(ConstantUtil.QUERY_PARAM_TITULO), ConstantUtil.PERCENT + part
                        + ConstantUtil.PERCENT),
                        builder.equal(from.get("arvore"), tipoArvore),
                        builder.equal(from.get("tipo"), TipoNodo.I),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A)
               )
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_TITULO)));
      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<CategoriaItemEntity> likeByEtiqueta(String etiqueta, Long idGrupoTrabalho, TipoArvore tipoArvore)
            throws CronosException
   {
      TypedQuery<CategoriaItemEntity> query = getEntityManager().createNamedQuery("CategoriaItemEntity.likeByEtiqueta",
               CategoriaItemEntity.class);

      query.setParameter("etiqueta", ConstantUtil.PERCENT + etiqueta.toUpperCase() + ConstantUtil.PERCENT);
      query.setParameter("arvore", tipoArvore);
      query.setParameter(ConstantUtil.QUERY_PARAM_SITUACAO, Situacao.A);
      query.setParameter(ConstantUtil.QUERY_PARAM_COMPARTILHAR, SimNao.S);
      query.setParameter(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO, idGrupoTrabalho);

      try
      {
         return query.getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<CategoriaItemEntity> likeByElemento(String elemento, Long idGrupoTrabalho, TipoArvore tipoArvore)
            throws CronosException
   {
      TypedQuery<CategoriaItemEntity> query = getEntityManager().createNamedQuery("CategoriaItemEntity.likeByElemento",
               CategoriaItemEntity.class);

      query.setParameter("elemento", ConstantUtil.PERCENT + elemento.toUpperCase() + ConstantUtil.PERCENT);
      query.setParameter("arvore", tipoArvore);
      query.setParameter(ConstantUtil.QUERY_PARAM_SITUACAO, Situacao.A);
      query.setParameter(ConstantUtil.QUERY_PARAM_COMPARTILHAR, SimNao.S);
      query.setParameter(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO, idGrupoTrabalho);

      try
      {
         return query.getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<CategoriaItemEntity> listPesquisaGeral(String part, TipoArvore tipoArvore, Long idGrupoTrabalho)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);
      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.like(builder.upper(from.<String> get(ConstantUtil.QUERY_PARAM_TITULO)),
                        ConstantUtil.PERCENT + part.toUpperCase()
                                 + ConstantUtil.PERCENT),
                        builder.equal(from.get("arvore"), tipoArvore),
                        builder.or(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                                 builder.equal(from.get(ConstantUtil.QUERY_PARAM_COMPARTILHAR), SimNao.S)),
                        builder.equal(from.get("tipo"), TipoNodo.I),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A)
               )
               .orderBy(builder.asc(from.get("titulo")));
      try
      {
         return createQuery(query).getResultList();
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   public List<CategoriaItemEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho, TipoArvore tipoArvore)
            throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<CategoriaItemEntity> query = builder.createQuery(CategoriaItemEntity.class);

      Root<CategoriaItemEntity> from = query.from(CategoriaItemEntity.class);

      query.select(from)
               .where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_ID_GRUPO_TRABALHO), idGrupoTrabalho),
                        builder.isNull(from.get(ConstantUtil.QUERY_PARAM_ID_PAI)),
                        builder.equal(from.get("arvore"), tipoArvore),
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
