package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.model.entity.TipoElementoEntity;
import br.jus.tjrs.cronos.app.model.repository.ElementoIGTRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ElementoIGTServiceImpl extends AbstractServiceEntity<Long, ElementoIGTEntity> implements
         ElementoIGTService
{
   @Inject
   private ElementoIGTRepository elementoIGTRepository;

   /**
    * Utilizado @EJB devido ao erro: WELD-001443 Pseudo scoped bean has circular dependencies.
    */
   @EJB
   CategoriaItemService categoriaItemService;

   @Override
   protected ElementoIGTRepository getEntityRepository()
   {
      return elementoIGTRepository;
   }

   @Override
   public List<ElementoIGTEntity> findAllByCategoria(Long idCategoria) throws CronosException
   {
      return getEntityRepository().findAllByCategoria(idCategoria);
   }

   @Override
   public List<ElementoIGTEntity> findElementoIGTPadrao(TipoArvore tipoArvore,
            List<TipoElementoEntity> listaTipoElemento)
   {
      List<ElementoIGTEntity> elementosfff = new ArrayList<ElementoIGTEntity>();

      if (TipoArvore.T.equals(tipoArvore) || TipoArvore.S.equals(tipoArvore))
      {
         long ordem = 0l;
         elementosfff.add(createElemento(listaTipoElemento, ++ordem, "EMENTA"));
         elementosfff.add(createElemento(listaTipoElemento, ++ordem, "RELAT"));
         elementosfff.add(createElemento(listaTipoElemento, ++ordem, "FUNDAMENTO"));
         elementosfff.add(createElemento(listaTipoElemento, ++ordem, "DISPOSITIVO"));
      }
      Collections.sort(elementosfff);
      return elementosfff;
   }

   private ElementoIGTEntity createElemento(List<TipoElementoEntity> listaTipoElemento, long ordem, String tipo)
   {
      ElementoIGTEntity elemento = new ElementoIGTEntity();
      elemento.setTipoElemento(buscaTipoElemento(listaTipoElemento, tipo));
      elemento.setIdTipoElemento(elemento.getTipoElemento().getId());
      elemento.setOrdem(ordem);
      return elemento;
   }

   private TipoElementoEntity buscaTipoElemento(List<TipoElementoEntity> listaTipoElemento, String tipo)
   {
      for (TipoElementoEntity tipoElemento : listaTipoElemento)
      {
         if (tipoElemento.getNomeItem().toUpperCase().startsWith(tipo))
         {
            return tipoElemento;
         }
      }
      return null;
   }

   @Override
   public List<ElementoIGTEntity> listByPartDescription(String part) throws CronosException
   {
      return getEntityRepository().listByPartDescription(part);
   }
}
