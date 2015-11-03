package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TipoElementoEntity;
import br.jus.tjrs.cronos.app.model.repository.TipoElementoRepository;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class TipoElementoServiceImpl extends AbstractServiceEntity<Long, TipoElementoEntity> implements
         TipoElementoService
{
   @Inject
   private TipoElementoRepository tipoElementoRepository;

   @Override
   protected TipoElementoRepository getEntityRepository()
   {
      return tipoElementoRepository;
   }

}
