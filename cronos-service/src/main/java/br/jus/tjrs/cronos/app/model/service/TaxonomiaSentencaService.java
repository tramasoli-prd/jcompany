package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaSentencaEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface TaxonomiaSentencaService extends EntityService<Long, TaxonomiaSentencaEntity>
{
   List<TaxonomiaSentencaEntity> findAllByCategoria(Long idCategoria) throws CronosException;

   List<TaxonomiaSentencaEntity> findAllBySentenca(Long idSentenca) throws CronosException;
}
