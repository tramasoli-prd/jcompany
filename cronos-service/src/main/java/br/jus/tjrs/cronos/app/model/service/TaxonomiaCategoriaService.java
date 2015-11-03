package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaCategoriaEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface TaxonomiaCategoriaService extends EntityService<Long, TaxonomiaCategoriaEntity>
{
   List<TaxonomiaCategoriaEntity> findAllByCategoria(Long idCategoria) throws CronosException;

   List<TaxonomiaCategoriaEntity> findAllByModelo(Long idModelo) throws CronosException;
}
