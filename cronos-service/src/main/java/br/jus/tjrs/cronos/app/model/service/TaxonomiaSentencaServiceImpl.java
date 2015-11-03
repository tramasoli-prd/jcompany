package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaSentencaEntity;
import br.jus.tjrs.cronos.app.model.repository.TaxonomiaSentencaRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TaxonomiaSentencaServiceImpl extends AbstractServiceEntity<Long, TaxonomiaSentencaEntity> implements
         TaxonomiaSentencaService
{
   @Inject
   private TaxonomiaSentencaRepository taxonomiaSentencaRepository;

   @Override
   protected TaxonomiaSentencaRepository getEntityRepository()
   {
      return taxonomiaSentencaRepository;
   }

   @Override
   public List<TaxonomiaSentencaEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      return getEntityRepository().findAllByCategoria(idCategoria);
   }

   @Override
   public List<TaxonomiaSentencaEntity> findAllBySentenca(Long idSentenca) throws CronosException
   {
      return getEntityRepository().findAllBySentenca(idSentenca);
   }
}
