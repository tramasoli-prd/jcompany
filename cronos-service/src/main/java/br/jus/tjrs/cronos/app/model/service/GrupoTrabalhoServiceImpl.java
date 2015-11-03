package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoLoginDTO;
import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoNodoDTO;
import br.jus.tjrs.cronos.app.model.entity.AlegacaoGTEntity;
import br.jus.tjrs.cronos.app.model.entity.ConfiguracaoEntity;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaUsuarioEntity;
import br.jus.tjrs.cronos.app.model.repository.GrupoTrabalhoRepository;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class GrupoTrabalhoServiceImpl extends AbstractServiceEntity<Long, GrupoTrabalhoEntity> implements
         GrupoTrabalhoService
{
   @Inject
   private GrupoTrabalhoRepository grupoTrabalhoRepository;

   /**
    * Utilizado @EJB devido ao erro: WELD-001443 Pseudo scoped bean has circular dependencies.
    */
   @EJB
   private CategoriaItemService categoriaItemService;

   @EJB
   private AlegacaoGTService alegacaoGTService;

   @EJB
   private ConfiguracaoService configuracaoService;

   @Inject
   private GrupoTrabalhoTreeViewBuilder grupoTrabalhoTreeViewBuilder;

   @EJB
   private UsuarioService usuarioService;

   @Inject
   private TaxonomiaUsuarioService taxonomiaUsuarioService;

   @Override
   public GrupoTrabalhoEntity createFirstTreeView(GrupoTrabalhoEntity grupoTrabalhoNovo,
            GrupoTrabalhoEntity grupoTrabalhoAntigo)
            throws CronosException
   {
      try
      {
         grupoTrabalhoNovo = save(grupoTrabalhoNovo);
         this.categoriaItemService.createFirstTreeView(grupoTrabalhoNovo, grupoTrabalhoAntigo);
         Long idGrupoTrabalho = grupoTrabalhoAntigo.getId();
         List<AlegacaoGTEntity> findAllByGrupoTrabalho = alegacaoGTService.findAllByGrupoTrabalho(idGrupoTrabalho);
         for (AlegacaoGTEntity alegacao : findAllByGrupoTrabalho)
         {
            this.alegacaoGTService.save(alegacao.clone(grupoTrabalhoNovo));
         }
         ConfiguracaoEntity configuracaoEntity = this.configuracaoService
                  .findConfiguracaoByGrupoTrabalho(idGrupoTrabalho);
         this.configuracaoService.save(configuracaoEntity.clone(grupoTrabalhoNovo));
         return grupoTrabalhoNovo;
      }
      catch (Exception e)
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }

   @Override
   public GrupoTrabalhoNodoDTO findTreeViewDTO(final Long idGrupoTrabalho) throws CronosException
   {
      GrupoTrabalhoEntity grupo = get(idGrupoTrabalho);
      return grupoTrabalhoTreeViewBuilder.build(grupo);
   }

   @Override
   protected GrupoTrabalhoRepository getEntityRepository()
   {
      return grupoTrabalhoRepository;
   }

   @Override
   public List<GrupoTrabalhoLoginDTO> findByUsuario(Long idUsuario) throws CronosException
   {
      List<TaxonomiaUsuarioEntity> findAllByGrupoTrabalho = this.taxonomiaUsuarioService.findAllByUsuario(idUsuario);
      List<GrupoTrabalhoLoginDTO> listaGrupo = new ArrayList<GrupoTrabalhoLoginDTO>();
      for (TaxonomiaUsuarioEntity taxonomiaUsuarioEntity : findAllByGrupoTrabalho)
      {
         GrupoTrabalhoEntity grupoTrabalho = taxonomiaUsuarioEntity.getGrupoTrabalho();
         GrupoTrabalhoLoginDTO gtl = new GrupoTrabalhoLoginDTO();
         gtl.setIdGrupoTrabalho(grupoTrabalho.getId());
         gtl.setNomeMagistrado(grupoTrabalho.getUsuario().getNome());
         gtl.setIdUsuario(idUsuario);
         gtl.setPadrao(taxonomiaUsuarioEntity.getPadrao());
         listaGrupo.add(gtl);
      }
      return listaGrupo;
   }

   @Override
   public boolean alteraPadrao(GrupoTrabalhoLoginDTO grupo) throws CronosException
   {
      List<TaxonomiaUsuarioEntity> findAllByGrupoTrabalho = this.taxonomiaUsuarioService.findAllByUsuario(grupo
               .getIdUsuario());
      try
      {
         for (TaxonomiaUsuarioEntity taxonomiaUsuarioEntity : findAllByGrupoTrabalho)
         {
            if (grupo.getIdGrupoTrabalho().longValue() == taxonomiaUsuarioEntity.getIdGrupoTrabalho().longValue())
            {
               taxonomiaUsuarioEntity.setPadrao(SimNao.S);
            }
            else
            {
               taxonomiaUsuarioEntity.setPadrao(SimNao.N);
            }

            this.taxonomiaUsuarioService.save(taxonomiaUsuarioEntity);
         }
         return true;
      }
      catch (Exception e)
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }

}
