package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface TaxonomiaEtiquetaGTService extends EntityService<Long, TaxonomiaEtiquetaGTEntity>
{
   List<TaxonomiaEtiquetaGTEntity> findAllByCategoria(Long idCategoria) throws CronosException;

   List<TaxonomiaEtiquetaGTEntity> findAllByEtiqueta(Long idEtiqueta) throws CronosException;
}
