package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.dto.IconeDTO;
import br.jus.tjrs.cronos.app.model.dto.ImagemDTO;
import br.jus.tjrs.cronos.app.model.entity.EtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.entity.IconesPersonalEntity;
import br.jus.tjrs.cronos.app.model.repository.EtiquetaGTRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class EtiquetaGTServiceImpl extends AbstractServiceEntity<Long, EtiquetaGTEntity> implements EtiquetaGTService
{
   @Inject
   private EtiquetaGTRepository etiquetaGTRepository;

   @Inject
   private IconesPersonalService iconesPersonalService;

   @Inject
   TaxonomiaEtiquetaGTService taxonomiaEtiquetaGTService;

   @Override
   protected EtiquetaGTRepository getEntityRepository()
   {
      return etiquetaGTRepository;
   }

   @Override
   public List<EtiquetaGTEntity> listByPartDescription(String part, Long idGrupoTrabalho) throws CronosException
   {
      return getEntityRepository().listByPartDescription(part, idGrupoTrabalho);
   }

   @Override
   public EtiquetaGTEntity findEtiquetaGrupoTrabalho(String etiqueta, Long idGrupoTrabalho) throws CronosException
   {
      return getEntityRepository().findEtiquetaGrupoTrabalho(etiqueta, idGrupoTrabalho);
   }

   @Override
   public EtiquetaGTEntity alterarIcone(IconeDTO iconeDTO) throws CronosException
   {
      EtiquetaGTEntity etiqueta = get(iconeDTO.getId());
      if (iconeDTO.getImagem() != null)
      {
         ImagemDTO imagem = iconeDTO.getImagem();
         IconesPersonalEntity icone = new IconesPersonalEntity();
         icone.setImagem(imagem.getBase64());
         icone.setTipo(imagem.getFiletype());
         icone.setUsuarioAtualizacao(iconeDTO.getUsuarioAtualizacao());
         icone = this.iconesPersonalService.save(icone);
         etiqueta.setIconePersonal(icone);
         etiqueta.setIdIcone(icone.getId());
         etiqueta.setCor(null);
         etiqueta.setIcone(null);
      }
      else
      {
         etiqueta.setCor(iconeDTO.getCor());
         etiqueta.setIcone(iconeDTO.getIcone());
         etiqueta.setIdIcone(null);
         etiqueta.setIconePersonal(null);
      }
      etiqueta.setUsuarioAtualizacao(iconeDTO.getUsuarioAtualizacao());
      return getEntityRepository().save(etiqueta);
   }
}
