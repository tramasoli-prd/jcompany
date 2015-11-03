package br.jus.tjrs.cronos.app.model.repository;

import javax.enterprise.context.ApplicationScoped;

import br.jus.tjrs.cronos.app.model.entity.FundamentoEntity;
import br.jus.tjrs.cronos.core.model.repository.AbstractCronosRepository;

@ApplicationScoped
public class FundamentoRepository extends AbstractCronosRepository<Long, FundamentoEntity>
{
   @Override
   public Class<FundamentoEntity> getEntityType()
   {
      return FundamentoEntity.class;
   }
}
