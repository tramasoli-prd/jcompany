package br.jus.tjrs.cronos.app.model.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.AlegacaoGTEntity;
import br.jus.tjrs.cronos.app.model.entity.TipoAlegacaoEntity;
import br.jus.tjrs.cronos.app.model.repository.AlegacaoGTRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class AlegacaoGTServiceImpl extends AbstractServiceEntity<Long, AlegacaoGTEntity> implements
         AlegacaoGTService
{
   @Inject
   private AlegacaoGTRepository alegacaoGTRepository;

   @EJB
   private GrupoTrabalhoService grupoTrabalhoService;

   @Override
   protected AlegacaoGTRepository getEntityRepository()
   {
      return alegacaoGTRepository;
   }

   @Override
   public List<AlegacaoGTEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      List<AlegacaoGTEntity> listaAlegacao = getEntityRepository().findAllByGrupoTrabalho(idGrupoTrabalho);
      Collections.sort(listaAlegacao, new Comparator<AlegacaoGTEntity>()
      {
         @Override
         public int compare(AlegacaoGTEntity alegacao1, AlegacaoGTEntity alegacao2)
         {

            if (alegacao1.getTextoAlegacao() == null)
            {
               return 0;
            }
            else if (alegacao2.getTextoAlegacao() == null)
            {
               return 0;
            }
            else
            {
               TipoAlegacaoEntity tipoAlegacao1 = alegacao1.getTipoAlegacao();
               TipoAlegacaoEntity tipoAlegacao2 = alegacao2.getTipoAlegacao();
               return tipoAlegacao1.getQuemAlega().compareTo(tipoAlegacao2.getQuemAlega());
            }
         }
      });
      return listaAlegacao;
   }
}