package br.jus.tjrs.cronos.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Permissoes;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity;
//import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity_;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class UsuarioRepository extends AbstractCronosRepository<Long, UsuarioEntity>
{
   public UsuarioEntity save(UsuarioEntity entity) throws CronosException
   {
      // TODO, mudar para o service
      if (entity.getPermissoes() == null)
      {
         entity.setPermissoes(Permissoes.R);
      }
      if (entity.getMagistrado() == null)
      {
         entity.setMagistrado(SimNao.N);
      }
      entity.setPadrao(SimNao.N);
      return super.save(entity);
   }

   public UsuarioEntity findByCpf(String cpf)
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<UsuarioEntity> query = builder.createQuery(UsuarioEntity.class);
      Root<UsuarioEntity> from = query.from(UsuarioEntity.class);
      query.select(from)
               .where(builder.equal(from.get("cpf"), cpf),
                        builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), Situacao.A));

      UsuarioEntity usuario;
      try
      {
         List<UsuarioEntity> lista = createQuery(query).getResultList();
         if (lista == null || lista.isEmpty())
         {
            return null;
         }
         else
         {
            return lista.get(0);
         }
      }
      catch (NoResultException e)
      {
         usuario = null;
      }
      return usuario;

   }

   public UsuarioEntity findUsuarioPadrao() throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();

      CriteriaQuery<UsuarioEntity> query = builder.createQuery(UsuarioEntity.class);

      Root<UsuarioEntity> from = query.from(UsuarioEntity.class);

      query.select(from)
               .where(builder.equal(from.get("padrao"), SimNao.S))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ID)));

      List<UsuarioEntity> usuarios;
      try
      {
         usuarios = createQuery(query).getResultList();
         return usuarios.get(0);
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }

   @Override
   public Class<UsuarioEntity> getEntityType()
   {
      return UsuarioEntity.class;
   }

   public UsuarioEntity findByLogin(String login) throws CronosException
   {
      CriteriaBuilder builder = criteriaBuilder();
      CriteriaQuery<UsuarioEntity> query = builder.createQuery(UsuarioEntity.class);
      Root<UsuarioEntity> from = query.from(UsuarioEntity.class);

      query.select(from)
               .where(builder.equal(from.get("login"), login))
               .orderBy(builder.asc(from.get(ConstantUtil.QUERY_PARAM_ID)));

      try
      {
         List<UsuarioEntity> lista = createQuery(query).getResultList();
         return lista.get(0);
      }
      catch (NoResultException e)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
   }
}
