package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaUsuarioEntity;
import br.jus.tjrs.cronos.app.model.repository.TaxonomiaUsuarioRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TaxonomiaUsuarioServiceImpl extends AbstractServiceEntity<Long, TaxonomiaUsuarioEntity> implements
         TaxonomiaUsuarioService
{
   @Inject
   private TaxonomiaUsuarioRepository taxonomiaUsuarioRepository;

   @Override
   protected TaxonomiaUsuarioRepository getEntityRepository()
   {
      return taxonomiaUsuarioRepository;
   }

   @Override
   public List<TaxonomiaUsuarioEntity> findAllByUsuario(Long idCategoria) throws CronosException
   {
      return getEntityRepository().findAllByUsuario(idCategoria);
   }

   @Override
   public List<TaxonomiaUsuarioEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      return getEntityRepository().findAllByGrupoTrabalho(idGrupoTrabalho);
   }

   @Override
   public Integer maxOrderGrupo(Long idGrupo) throws CronosException
   {
      return this.getEntityRepository().maxOrderGrupo(idGrupo);
   }

   @Override
   public Integer maxOrderUsuario(Long idUsuario) throws CronosException
   {
      return this.getEntityRepository().maxOrderUsuario(idUsuario);
   }
   
   @Override
   public List<TaxonomiaUsuarioEntity> findAllByUsuarioGrupoTrabalho(Long idUsuario, Long idGrupoTrabalho) throws CronosException
   {
      return this.getEntityRepository().findAllByUsuarioGrupoTrabalho(idUsuario, idGrupoTrabalho);
   }
}
