package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.dto.CssDTO;
import br.jus.tjrs.cronos.app.model.entity.IconesPersonalEntity;
import br.jus.tjrs.cronos.app.model.repository.IconesPersonalRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class IconesPersonalServiceImpl extends AbstractServiceEntity<Long, IconesPersonalEntity> implements
         IconesPersonalService
{
   private static final int CSS_AVERAGE_SIZE = 128;

   @Inject
   private IconesPersonalRepository iconesPersonalRepository;

   @Override
   protected IconesPersonalRepository getEntityRepository()
   {
      return iconesPersonalRepository;
   }

   @Override
   public CssDTO css() throws CronosException
   {
      CssDTO css = new CssDTO();
      List<IconesPersonalEntity> lista = getEntityRepository().findAll();
      StringBuilder builderIcone = new StringBuilder(lista.size() * CSS_AVERAGE_SIZE);
      for (IconesPersonalEntity icones : lista)
      {
         builderIcone.append(".iconespersonal_").append(icones.getId()).append("{").append("content:")
                  .append("url(data:").append(icones.getTipo()).append(";").append("base64,")
                  .append(icones.getImagem()).append(")").append(";}");
      }
      css.setCss(builderIcone.toString());
      return css;
   }
}
