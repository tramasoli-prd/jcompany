package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.model.entity.TipoElementoEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

public interface ElementoIGTService extends EntityService<Long, ElementoIGTEntity>
{
   List<ElementoIGTEntity> findAllByCategoria(Long idCategoria) throws CronosException;

   List<ElementoIGTEntity> findElementoIGTPadrao(TipoArvore tipoArvore, List<TipoElementoEntity> listaElementos)
            throws CronosException;

   List<ElementoIGTEntity> listByPartDescription(String part) throws CronosException;
}
