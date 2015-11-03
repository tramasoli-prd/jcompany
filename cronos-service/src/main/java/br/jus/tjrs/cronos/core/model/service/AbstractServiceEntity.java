package br.jus.tjrs.cronos.core.model.service;

import java.io.Serializable;
import java.util.List;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.commons.search.PagedResult;
import br.jus.tjrs.cronos.commons.search.Pagination;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;
import br.jus.tjrs.cronos.core.model.repository.EntityRepository;

public abstract class AbstractServiceEntity<PK extends Serializable, E extends VersionedEntity<PK>> implements
         EntityService<PK, E>
{
   protected abstract EntityRepository<PK, E> getEntityRepository();

   @Override
   public E newEntity()
   {
      try
      {
         return getEntityRepository().getEntityType().newInstance();
      }
      catch (InstantiationException | IllegalAccessException e)
      {
         throw new IllegalStateException("Entidade n�o possue construtor padr�o");
      }
   }

   @Override
   public List<E> findAll() throws CronosException
   {
      return getEntityRepository().findAll();
   }

   @Override
   public PagedResult<E> find(Pagination<E> config) throws CronosException
   {
      return getEntityRepository().find(config);
   }

   @Override
   public E get(PK id)
   {
      return getEntityRepository().get(id);
   }

   @Override
   public E save(E entity) throws CronosException
   {
      return getEntityRepository().save(entity);
   }

   @Override
   public void remove(E entity) throws CronosException
   {
      try
      {
         E novo = get(entity.getId());
         novo.setUsuarioAtualizacao(entity.getUsuarioAtualizacao());
         novo.setVersao(entity.getVersao());
         getEntityRepository().remove(novo);
      }
      catch (Exception e)
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }
   
   @Override
   public void inative(E entity) throws CronosException
   {
      try
      {
         E novo = get(entity.getId());
         novo.setUsuarioAtualizacao(entity.getUsuarioAtualizacao());
         novo.setVersao(entity.getVersao());
         getEntityRepository().inative(novo);
      }
      catch (Exception e)
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }
}
