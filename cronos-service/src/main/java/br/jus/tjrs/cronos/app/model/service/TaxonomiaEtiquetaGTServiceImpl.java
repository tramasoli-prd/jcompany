package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.repository.TaxonomiaEtiquetaGTRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TaxonomiaEtiquetaGTServiceImpl extends AbstractServiceEntity<Long, TaxonomiaEtiquetaGTEntity> implements
         TaxonomiaEtiquetaGTService
{
   @Inject
   private TaxonomiaEtiquetaGTRepository taxonomiaEtiquetaGTRepository;

   @Override
   protected TaxonomiaEtiquetaGTRepository getEntityRepository()
   {
      return taxonomiaEtiquetaGTRepository;
   }

   @Override
   public List<TaxonomiaEtiquetaGTEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      return getEntityRepository().findAllByCategoria(idCategoria);
   }

   @Override
   public List<TaxonomiaEtiquetaGTEntity> findAllByEtiqueta(Long idEtiqueta) throws CronosException
   {
      return getEntityRepository().findAllByEtiqueta(idEtiqueta);
   }

}
