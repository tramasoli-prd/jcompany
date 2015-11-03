package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.Situacao;
import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.domain.TipoNodo;
import br.jus.tjrs.cronos.app.model.dto.AbstractNodeDTO;
import br.jus.tjrs.cronos.app.model.dto.CategoriaNodoDTO;
import br.jus.tjrs.cronos.app.model.dto.EtiquetaNodoDTO;
import br.jus.tjrs.cronos.app.model.dto.GrupoTrabalhoNodoDTO;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.model.entity.EtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.model.entity.SentencaEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.entity.TipoCategoriaEntity;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;

@Stateless
public class GrupoTrabalhoTreeViewBuilder
{

   @Inject
   private CategoriaItemService categoriaItemService;

   @Inject
   private TaxonomiaEtiquetaGTService taxonomiaEtiquetaGTService;

   @Inject
   private TipoCategoriaService tipoCategoriaService;

   @Inject
   private SentencaService sentencaService;

   private int limiteNivel = 2;

   private Long idNodo = 0l;

   public void setLimiteNivel(int limiteNivel)
   {
      this.limiteNivel = limiteNivel;
   }

   public int getLimiteNivel()
   {
      return limiteNivel;
   }

   public GrupoTrabalhoNodoDTO build(GrupoTrabalhoEntity grupo) throws CronosException
   {
      List<CategoriaItemEntity> lista = categoriaItemService.findAllByGrupoTrabalho(grupo.getId());
      GrupoTrabalhoNodoDTO buildItens = buildItens(new GrupoTrabalhoNodoDTO(grupo.getId()), lista, 0);
      return buildSentenca(buildItens);
   }

   private GrupoTrabalhoNodoDTO buildSentenca(GrupoTrabalhoNodoDTO grupo) throws CronosException
   {
      List<TipoCategoriaEntity> findAll = tipoCategoriaService.findAll();
      String sentenca = null;
      for (TipoCategoriaEntity tipoCategoriaEntity : findAll)
      {
         if (tipoCategoriaEntity.getNome().toUpperCase().startsWith(ConstantUtil.SENTENCA_START_WITH))
         {
            sentenca = tipoCategoriaEntity.getNome();
            break;
         }
      }
      idNodo++;
      CategoriaNodoDTO dto = new CategoriaNodoDTO();
      dto.setOrdem(3);
      dto.setTipo(TipoNodo.N);
      dto.setTipoArvore(TipoArvore.S);
      dto.setLabel(sentenca);
      dto.setIdNodo(idNodo);
      grupo.getItens().add(dto);
      createNodoSentenca(ConstantUtil.SENTENCA_INICIADA, 1, dto, SimNao.N, grupo.getId());
      createNodoSentenca(ConstantUtil.SENTENCA_FINALIZADA, 2, dto, SimNao.S, grupo.getId());
      return grupo;
   }

   private void createNodoSentenca(String label, int ordem, CategoriaNodoDTO sentenca, SimNao finalizada,
            Long idGrupoTrabalho)
            throws CronosException
   {
      idNodo++;
      CategoriaNodoDTO dto = new CategoriaNodoDTO();
      dto.setOrdem(ordem);
      dto.setTipo(TipoNodo.N);
      dto.setTipoArvore(TipoArvore.S);
      dto.setLabel(label);
      dto.setIdNodo(idNodo);
      List<SentencaEntity> sentencas;
      if (SimNao.S.equals(finalizada))
      {
         sentencas = sentencaService.listByGrupoTrabalhoFinalizada(idGrupoTrabalho, finalizada);
         sentencas = inativarSentenca(sentencas);
      }
      else
      {
         sentencas = sentencaService.listByGrupoTrabalhoFinalizada(idGrupoTrabalho, finalizada);
      }
      geraNodoSentenca(dto, sentencas);
      sentenca.getItens().add(dto);
   }

   private List<SentencaEntity> inativarSentenca(List<SentencaEntity> sentencas) throws CronosException
   {
      Date hoje = new Date(System.currentTimeMillis());
      Calendar cal = Calendar.getInstance();
      cal.setTime(hoje);
      cal.add(Calendar.DAY_OF_MONTH, -90);
      Date hoje90 = cal.getTime();

      List<SentencaEntity> sentencasNovas = new ArrayList<SentencaEntity>();
      for (SentencaEntity sentenca : sentencas)
      {
         /* Sentença com data inferior a 90 dias, deve ser inativada */
         if (sentenca.getDataAtualizacao().compareTo(hoje90) < 0)
         {
            this.sentencaService.inative(sentenca);
         }
         else
         {
            sentencasNovas.add(sentenca);
         }
      }
      return sentencasNovas;

   }

   private void geraNodoSentenca(CategoriaNodoDTO dtoFinalizada, List<SentencaEntity> sentencasFinalizada)
   {
      for (SentencaEntity sentencaEntity : sentencasFinalizada)
      {
         idNodo++;
         if (sentencaEntity.getSituacao().equals(Situacao.E))
         {
            continue;
         }
         AbstractNodeDTO nodoCategoria = createSentencaNodoDTO(sentencaEntity);
         nodoCategoria.setIdNodo(idNodo);
         dtoFinalizada.getItens().add(nodoCategoria);
      }
   }

   private <N extends AbstractNodeDTO> N buildItens(N parent, List<CategoriaItemEntity> itens, int nivel)
            throws CronosException
   {
      if (nivel > limiteNivel)
      {
         return parent;
      }
      for (CategoriaItemEntity categoriaItem : itens)
      {
         idNodo++;
         if (categoriaItem.getSituacao().equals(Situacao.E))
         {
            continue;
         }
         AbstractNodeDTO nodoCategoria = createNodoDTO(categoriaItem);
         nodoCategoria.setIdNodo(idNodo);
         if (TipoNodo.I.equals(categoriaItem.getTipo()))
         {
            List<TaxonomiaEtiquetaGTEntity> listaTaxonomia = this.taxonomiaEtiquetaGTService
                     .findAllByCategoria(categoriaItem.getId());
            for (TaxonomiaEtiquetaGTEntity taxonomia : listaTaxonomia)
            {
               idNodo++;
               AbstractNodeDTO etiqueta = getEtiquetaDTO(parent, taxonomia);
               etiqueta.setIdNodo(idNodo);
               idNodo++;
               AbstractNodeDTO nodoCategoriaN = createNodoDTO(categoriaItem);
               nodoCategoriaN.setIdNodo(idNodo);
               etiqueta.getItens().add(nodoCategoriaN);
               Collections.sort(etiqueta.getItens());
            }
         }
         else
         {
            parent.getItens().add(nodoCategoria);
            Collections.sort(parent.getItens());
         }
         List<CategoriaItemEntity> lista;
         try
         {
            lista = this.categoriaItemService.findAllByPai(nodoCategoria.getId());
         }
         catch (CronosException c)
         {
            return parent;
         }
         buildItens(nodoCategoria, lista, nivel);
      }
      return parent;
   }

   private AbstractNodeDTO getEtiquetaDTO(AbstractNodeDTO parent, TaxonomiaEtiquetaGTEntity taxonomia)
   {
      EtiquetaGTEntity etiquetaGT = taxonomia.getEtiquetaGT();
      for (AbstractNodeDTO nodo : parent.getItens())
      {
         if (TipoNodo.E.equals(nodo.getTipo())
                  && StringUtils.equals(etiquetaGT.getEtiqueta(), nodo.getLabel()))
         {
            return nodo;
         }
      }
      EtiquetaNodoDTO nodo = createEtiquetaDTO(etiquetaGT, taxonomia);
      parent.getItens().add(nodo);
      Collections.sort(parent.getItens());
      return nodo;
   }

   private CategoriaNodoDTO createNodoDTO(CategoriaItemEntity categoria)
   {
      CategoriaNodoDTO dto = new CategoriaNodoDTO();
      dto.setId(categoria.getId());
      dto.setCompartilhar(categoria.getCompartilhar());
      dto.setIcone(categoria.getIcone());
      dto.setIdIcone(categoria.getIdIcone());
      dto.setIconeCor(categoria.getCor());
      dto.setOrdem(categoria.getOrdem());
      dto.setTipo(categoria.getTipo());
      dto.setTipoArvore(categoria.getArvore());
      if (TipoNodo.N.equals(categoria.getTipo()) && categoria.getTitulo() == null)
      {
         dto.setLabel(categoria.getTipoCategoria().getNome());
      }
      else
      {
         dto.setLabel(categoria.getTitulo());
      }
      return dto;
   }

   private CategoriaNodoDTO createSentencaNodoDTO(SentencaEntity categoria)
   {
      CategoriaNodoDTO dto = new CategoriaNodoDTO();
      dto.setId(categoria.getId());
      dto.setIcone(categoria.getIcone());
      dto.setIdIcone(categoria.getIdIcone());
      dto.setIconeCor(categoria.getCor());
      // dto.setOrdem(categoria.getOrdem());
      dto.setTipo(TipoNodo.I);
      dto.setTipoArvore(TipoArvore.S);
      dto.setLabel(categoria.getNumeroProcesso());
      return dto;
   }

   private EtiquetaNodoDTO createEtiquetaDTO(EtiquetaGTEntity etiqueta, TaxonomiaEtiquetaGTEntity taxonomia)
   {
      EtiquetaNodoDTO dto = new EtiquetaNodoDTO();
      dto.setId(etiqueta.getId());
      dto.setIcone(etiqueta.getIcone());
      dto.setLabel(etiqueta.getEtiqueta());
      dto.setIdIcone(etiqueta.getIdIcone());
      dto.setTipo(TipoNodo.E);
      dto.setIconeCor(etiqueta.getCor());
      dto.setOrdem(taxonomia.getOrdem());
      return dto;
   }
}
