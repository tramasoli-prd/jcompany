package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TipoCategoriaEntity;
import br.jus.tjrs.cronos.app.model.repository.TipoCategoriaRepository;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class TipoCategoriaServiceImpl extends AbstractServiceEntity<Long, TipoCategoriaEntity> implements
         TipoCategoriaService
{
   @Inject
   private TipoCategoriaRepository tipoCategoriaRepository;

   @Override
   protected TipoCategoriaRepository getEntityRepository()
   {
      return tipoCategoriaRepository;
   }

}
