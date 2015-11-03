package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaCategoriaEntity;
import br.jus.tjrs.cronos.app.model.repository.TaxonomiaCategoriaRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TaxonomiaCategoriaServiceImpl extends AbstractServiceEntity<Long, TaxonomiaCategoriaEntity> implements
         TaxonomiaCategoriaService
{
   @Inject
   private TaxonomiaCategoriaRepository taxonomiaCategoriaRepository;

   @Override
   protected TaxonomiaCategoriaRepository getEntityRepository()
   {
      return taxonomiaCategoriaRepository;
   }

   @Override
   public List<TaxonomiaCategoriaEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      return getEntityRepository().findAllByCategoria(idCategoria);
   }

   @Override
   public List<TaxonomiaCategoriaEntity> findAllByModelo(Long idModelo) throws CronosException
   {
      return getEntityRepository().findAllByModelo(idModelo);
   }
}
