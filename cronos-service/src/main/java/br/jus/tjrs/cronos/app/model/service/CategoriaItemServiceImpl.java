package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.Permissoes;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.domain.TipoLabel;
import br.jus.tjrs.cronos.app.model.domain.TipoNodo;
import br.jus.tjrs.cronos.app.model.dto.AutoCompleteDTO;
import br.jus.tjrs.cronos.app.model.dto.CarregarCategoriaDTO;
import br.jus.tjrs.cronos.app.model.dto.CategoriaItemDTO;
import br.jus.tjrs.cronos.app.model.dto.CloneCategoriaItemDTO;
import br.jus.tjrs.cronos.app.model.dto.IconeDTO;
import br.jus.tjrs.cronos.app.model.dto.ImagemDTO;
import br.jus.tjrs.cronos.app.model.dto.LabelDTO;
import br.jus.tjrs.cronos.app.model.dto.PaiDTO;
import br.jus.tjrs.cronos.app.model.dto.PesquisaGeralDTO;
import br.jus.tjrs.cronos.app.model.dto.RetornoPesquisaGeralDTO;
import br.jus.tjrs.cronos.app.model.dto.SaveNodeDTO;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.model.entity.EtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.app.model.entity.IconesPersonalEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaCategoriaEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;
import br.jus.tjrs.cronos.app.model.entity.TipoCategoriaEntity;
import br.jus.tjrs.cronos.app.model.entity.UsuarioEntity;
import br.jus.tjrs.cronos.app.model.repository.CategoriaItemRepository;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class CategoriaItemServiceImpl extends AbstractServiceEntity<Long, CategoriaItemEntity> implements
         CategoriaItemService
{

   private static final Logger LOG = LoggerFactory.getLogger(CategoriaItemServiceImpl.class);

   @Inject
   private CategoriaItemRepository categoriaItemRepository;

   @Inject
   private TaxonomiaEtiquetaGTService taxonomiaEtiquetaGTService;

   @Inject
   private TaxonomiaCategoriaService taxonomiaCategoriaService;

   @Inject
   private TipoElementoService tipoElementoService;

   @Inject
   private TipoCategoriaService tipoCategoriaService;

   @Inject
   private IconesPersonalService iconesPersonalService;

   @Inject
   private EtiquetaGTService etiquetaGTService;

   @Inject
   private UsuarioService usuarioService;

   /**
    * Utilizado @EJB devido ao erro: WELD-001443 Pseudo scoped bean has circular dependencies.
    */
   @EJB
   private GrupoTrabalhoService grupoTrabalhoService;

   @EJB
   private ElementoIGTService elementoIGTService;

   Map<String, UsuarioEntity> mapaUsuario = new HashMap<String, UsuarioEntity>();

   Map<String, EtiquetaGTEntity> mapaEtiquetas = new HashMap<String, EtiquetaGTEntity>();

   Map<Long, CategoriaItemEntity> mapaCategorias = new HashMap<Long, CategoriaItemEntity>();

   @Override
   protected CategoriaItemRepository getEntityRepository()
   {
      return categoriaItemRepository;
   }

   @Override
   public void createFirstTreeView(GrupoTrabalhoEntity grupoTrabalhoNovo, GrupoTrabalhoEntity grupoTrabalhoAntigo)
            throws CronosException
   {
      mapaEtiquetas.clear();
      mapaCategorias.clear();
      List<CategoriaItemEntity> listaTopicos = findAllByGrupoTrabalhoTipoArvore(grupoTrabalhoAntigo.getId(),
               TipoArvore.T);
      for (CategoriaItemEntity item : listaTopicos)
      {
         clonarCategoria(item.clone(grupoTrabalhoNovo, null), item, TipoArvore.T);
      }
      List<CategoriaItemEntity> listaModelos = findAllByGrupoTrabalhoTipoArvore(grupoTrabalhoAntigo.getId(),
               TipoArvore.M);
      for (CategoriaItemEntity item : listaModelos)
      {
         clonarCategoria(item.clone(grupoTrabalhoNovo, null), item, TipoArvore.M);
      }
   }

   /**
    * M�todo utilizado para criar a estrutura base do magistrado.
    */
   private void clonarCategoria(CategoriaItemEntity entity, CategoriaItemEntity originalItem, TipoArvore tipoArvore)
            throws CronosException
   {
      try
      {
         Long idCategoriaOld = originalItem.getId();
         CategoriaItemEntity categoria = retornaCategoriaInserida(idCategoriaOld);
         if (categoria == null)
         {
            categoria = this.save(entity);
            mapaCategorias.put(idCategoriaOld, entity);
         }
         List<CategoriaItemEntity> listaCategorias = findAllByPai(originalItem.getId());
         for (CategoriaItemEntity item : listaCategorias)
         {
            this.clonarCategoria(item.clone(categoria.getGrupoTrabalho(), categoria), item, tipoArvore);
         }
         cloneTaxonomiaEtiqueta(originalItem.getUsuarioAtualizacao(), originalItem, categoria);
         if (tipoArvore.equals(TipoArvore.M))
         {
            cloneTaxonomiaCategoria(originalItem.getUsuarioAtualizacao(), originalItem, categoria);
         }
         cloneElementos(originalItem.getUsuarioAtualizacao(), originalItem, categoria);
      }
      catch (Exception e)
      {
         LOG.error("Erro ao criar estrutura do magistrado", e);
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }

   private CategoriaItemEntity retornaCategoriaInserida(Long idCategoriaOld)
   {
      for (Entry<Long, CategoriaItemEntity> entry : mapaCategorias.entrySet())
      {
         if (entry.getKey().longValue() == idCategoriaOld)
         {
            return entry.getValue();
         }
      }
      return null;
   }

   /**
    * M�todo Respons�vel por buscar todas as {@link CategoriaItemEntity} pertencentes a um determinado pai
    * */
   public List<CategoriaItemEntity> findAllByPai(Long idCategoria) throws CronosException
   {
      return getEntityRepository().findAllByPai(idCategoria);
   }

   /**
    * M�todo Respons�vel por buscar o pai de um tipoArvore e GrupoTrabalho
    * */
   private CategoriaItemEntity findPaiByTipoArvoreGrupoTrabalho(TipoArvore tipoArvore, Long grupoTrabalho)
            throws CronosException
   {
      return this.getEntityRepository().findPaiByTipoArvoreGrupoTrabalho(tipoArvore, grupoTrabalho);
   }

   @Override
   public CategoriaItemDTO carregarCategoria(CarregarCategoriaDTO carregarCategoria)
            throws CronosException
   {
      CategoriaItemDTO categoriaDTO = new CategoriaItemDTO();
      CategoriaItemEntity categoria = new CategoriaItemEntity();

      TipoArvore tipoArvore = carregarCategoria.getTipoArvore();
      GrupoTrabalhoEntity grupoTrabalho;
      if (carregarCategoria.getIdCategoria() != null)
      {
         categoria = this.getEntityRepository().getDetached(carregarCategoria.getIdCategoria());
         tipoArvore = categoria.getArvore();

         grupoTrabalho = this.grupoTrabalhoService.get(categoria.getIdGrupoTrabalho());
      }
      else
      {
         grupoTrabalho = buscaGrupoTrabalho(carregarCategoria.getIdGrupoTrabalho());
      }
      Long idGrupoTrabalho = grupoTrabalho.getId();

      Long idCategoria = categoria.getId();

      CategoriaItemEntity categoriaPaiGeral = findPaiByTipoArvoreGrupoTrabalho(tipoArvore, idGrupoTrabalho);
      Long idCategoriaPaiGeral = categoriaPaiGeral.getId();
      if (TipoArvore.M.equals(tipoArvore))
      {
         categoriaDTO.getCategoriasModelo().addAll(findAllByPai(idCategoriaPaiGeral));
      }
      categoriaDTO.setPaiGeral(createPai(categoriaPaiGeral.getTipoCategoria().getNome(), idCategoriaPaiGeral));
      categoria.setElementos(carregarElementos(idCategoria, tipoArvore));
      categoria.setTaxonomiasEtiqueta(carregarTaxonomiaEtiqueta(idCategoria));
      categoriaDTO.setPai(carregarPai(categoria, categoriaPaiGeral, tipoArvore));
      categoriaDTO.setTaxonomiasCategoriaNovas(carregarTaxonomiaCategoria(idCategoria));

      if (idCategoria == null)
      {
         categoria.setTipoCategoria(carregaTipoCategoria(this.tipoCategoriaService.findAll(), TipoNodo.I));
         categoria.setArvore(tipoArvore);
         categoria.setTipo(TipoNodo.I);
         categoria.setGrupoTrabalho(grupoTrabalho);
         categoria.setIdGrupoTrabalho(idGrupoTrabalho);
      }
      categoria.setDescricaoArvore(StringEscapeUtils.escapeHtml4(tipoArvore.toString()));
      categoriaDTO.setCategoria(categoria);
      return categoriaDTO;
   }

   private List<TaxonomiaCategoriaEntity> carregarTaxonomiaCategoria(Long idCategoria) throws CronosException
   {
      List<TaxonomiaCategoriaEntity> taxonomiasCategoria = new ArrayList<TaxonomiaCategoriaEntity>();
      if (idCategoria != null)
      {
         taxonomiasCategoria.addAll(this.taxonomiaCategoriaService.findAllByModelo(idCategoria));
         Collections.sort(taxonomiasCategoria);
      }
      return taxonomiasCategoria;
   }

   private List<TaxonomiaEtiquetaGTEntity> carregarTaxonomiaEtiqueta(Long idCategoria) throws CronosException
   {
      List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiqueta = new ArrayList<TaxonomiaEtiquetaGTEntity>();
      if (idCategoria != null)
      {
         taxonomiasEtiqueta.addAll(this.taxonomiaEtiquetaGTService.findAllByCategoria(idCategoria));
         Collections.sort(taxonomiasEtiqueta);
      }
      return taxonomiasEtiqueta;
   }

   private PaiDTO carregarPai(CategoriaItemEntity categoria, CategoriaItemEntity categoriaPaiGeral,
            TipoArvore tipoArvore)
   {
      final Long idCategoriaPaiGeral = categoriaPaiGeral.getId();
      final Long idCategoria = categoria.getId();
      if (idCategoria != null && categoria.getIdPai().longValue() != idCategoriaPaiGeral.longValue())
      {
         final CategoriaItemEntity subpai = categoria.getPai();
         return createPai(subpai.getTitulo(), subpai.getId());
      }
      else if (idCategoria == null && TipoArvore.T.equals(tipoArvore))
      {
         categoria.setPai(categoriaPaiGeral);
         categoria.setIdPai(idCategoriaPaiGeral);
      }
      return null;

   }

   private List<ElementoIGTEntity> carregarElementos(Long idCategoria, TipoArvore tipoArvore) throws CronosException
   {
      List<ElementoIGTEntity> elementos = new ArrayList<ElementoIGTEntity>();
      if (idCategoria != null)
      {
         elementos = this.elementoIGTService.findAllByCategoria(idCategoria);
      }
      else
      {
         elementos = this.elementoIGTService.findElementoIGTPadrao(tipoArvore, this.tipoElementoService.findAll());
      }
      Collections.sort(elementos);
      return elementos;
   }

   private PaiDTO createPai(String descricao, Long idCategoriaPaiGeral)
   {
      PaiDTO paiGeral = new PaiDTO();
      paiGeral.setId(idCategoriaPaiGeral);
      paiGeral.setTitulo(descricao);
      return paiGeral;
   }

   private TipoCategoriaEntity carregaTipoCategoria(List<TipoCategoriaEntity> findAllCategoria, TipoNodo tipoNodo)
   {
      for (TipoCategoriaEntity tipoCategoriaEntity : findAllCategoria)
      {
         if (tipoCategoriaEntity.getNome().equalsIgnoreCase(tipoNodo.toString()))
         {
            return tipoCategoriaEntity;
         }
      }
      return null;
   }

   public List<CategoriaItemEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      return getEntityRepository().findAllByGrupoTrabalho(idGrupoTrabalho);
   }

   public CategoriaItemEntity salvarTopico(CategoriaItemDTO categoriaItemDTO) throws CronosException
   {
      boolean novo = false;
      CategoriaItemEntity categoria = categoriaItemDTO.getCategoria();
      String usuarioAtualizacao = categoriaItemDTO.getCategoria().getUsuarioAtualizacao();

      /* Guarda as listas para posterior persist�ncia */
      List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiquetaNovas = categoria.getTaxonomiasEtiqueta();
      List<TaxonomiaCategoriaEntity> taxonomiasCategoriaNovas = categoriaItemDTO.getTaxonomiasCategoriaNovas();
      List<ElementoIGTEntity> elementosNovos = categoria.getElementos();

      categoria.setElementos(null);
      categoria.setTaxonomiasEtiqueta(null);

      if (categoria.getId() == null)
      {
         novo = true;
         Integer ordemmax = maxOrderByPai(categoria.getIdPai());
         categoria.setOrdem((ordemmax == null ? 0 : ordemmax) + 1);
      }
      CategoriaItemEntity categoriaNova = save(categoria);
      if (novo)
      {
         salvarElementos(elementosNovos, categoriaNova, usuarioAtualizacao);
         salvarTaxonomiasEtiqueta(taxonomiasEtiquetaNovas, categoriaNova, usuarioAtualizacao);

         categoria.setElementos(elementosNovos);
         categoria.setTaxonomiasEtiqueta(taxonomiasEtiquetaNovas);
         categoriaItemDTO.setTaxonomiasCategoriaNovas(taxonomiasCategoriaNovas);
      }
      else
      {
         removeElementos(categoriaItemDTO, elementosNovos, usuarioAtualizacao);
         removeTaxonimiaEtiqueta(categoriaItemDTO, taxonomiasEtiquetaNovas, usuarioAtualizacao);
         removeTaxonomiaCategoria(categoriaItemDTO, taxonomiasCategoriaNovas, usuarioAtualizacao);
         salvarElementos(elementosNovos, categoriaNova, usuarioAtualizacao);
         salvarTaxonomiasEtiqueta(taxonomiasEtiquetaNovas, categoriaNova, usuarioAtualizacao);
      }
      salvarTaxonomiasCategoria(taxonomiasCategoriaNovas, categoriaNova, usuarioAtualizacao);
      return categoriaNova;
   }

   private void removeElementos(CategoriaItemDTO categoriaItemDTO, List<ElementoIGTEntity> elementos,
            String usuarioAtualizacao)
            throws CronosException
   {
      for (ElementoIGTEntity elementoAntigo : categoriaItemDTO.getElementos())
      {
         boolean existe = false;
         for (ElementoIGTEntity elementoNovo : elementos)
         {
            if (elementoNovo.getId() != null
                     && elementoAntigo.getId().longValue() == elementoNovo.getId().longValue())
            {
               existe = true;
            }
         }
         if (existe)
         {
            continue;
         }
         elementoAntigo.setUsuarioAtualizacao(usuarioAtualizacao);
         this.elementoIGTService.remove(elementoAntigo);
      }
   }

   private void removeTaxonimiaEtiqueta(CategoriaItemDTO categoriaItemDTO,
            List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiqueta, String usuarioAtualizacao) throws CronosException
   {
      for (TaxonomiaEtiquetaGTEntity taxonomiaEtiquetaAntiga : categoriaItemDTO.getTaxonomiasEtiqueta())
      {
         boolean existe = false;
         for (TaxonomiaEtiquetaGTEntity taxonomiaEtiquetaNova : taxonomiasEtiqueta)
         {
            if (taxonomiaEtiquetaNova.getId() != null
                     && taxonomiaEtiquetaAntiga.getId().longValue() == taxonomiaEtiquetaNova.getId().longValue())
            {
               existe = true;
            }
         }
         if (existe)
         {
            continue;
         }
         taxonomiaEtiquetaAntiga.setUsuarioAtualizacao(usuarioAtualizacao);
         this.taxonomiaEtiquetaGTService.remove(taxonomiaEtiquetaAntiga);
      }
   }

   private void removeTaxonomiaCategoria(CategoriaItemDTO categoriaItemDTO,
            List<TaxonomiaCategoriaEntity> taxonomiasCategoriaNovas, String usuarioAtualizacao) throws CronosException
   {
      for (TaxonomiaCategoriaEntity taxonomiaCategoriaAntiga : categoriaItemDTO.getTaxonomiasCategoria())
      {
         boolean existe = false;
         for (TaxonomiaCategoriaEntity taxonomiaCategoriaNova : taxonomiasCategoriaNovas)
         {
            if (taxonomiaCategoriaNova.getId() != null
                     && taxonomiaCategoriaAntiga.getId().longValue() == taxonomiaCategoriaNova.getId().longValue())
            {
               existe = true;
            }
         }
         if (existe)
         {
            continue;
         }
         this.taxonomiaCategoriaService.remove(taxonomiaCategoriaAntiga);
      }
   }

   private void salvarElementos(List<ElementoIGTEntity> elementos, CategoriaItemEntity categoria,
            String usuarioAtualizacao)
            throws CronosException
   {
      for (ElementoIGTEntity elemento : elementos)
      {
         elemento.setCategoria(categoria);
         elemento.setIdCategoria(categoria.getId());
         elemento.setUsuarioAtualizacao(usuarioAtualizacao);
         this.elementoIGTService.save(elemento);
      }
   }

   private void salvarTaxonomiasEtiqueta(List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiquetas,
            CategoriaItemEntity categoria, String usuarioAtualizacao)
            throws CronosException
   {
      for (TaxonomiaEtiquetaGTEntity taxonomiaEtiqueta : taxonomiasEtiquetas)
      {
         taxonomiaEtiqueta.setCategoriaItem(categoria);
         taxonomiaEtiqueta.setIdCategoria(categoria.getId());
         taxonomiaEtiqueta.setUsuarioAtualizacao(usuarioAtualizacao);
         this.taxonomiaEtiquetaGTService.save(taxonomiaEtiqueta);
      }
   }

   private void salvarTaxonomiasCategoria(List<TaxonomiaCategoriaEntity> taxonomiasCategoria,
            CategoriaItemEntity categoria, String usuarioAtualizacao)
            throws CronosException
   {
      for (TaxonomiaCategoriaEntity taxonomiaCategoria : taxonomiasCategoria)
      {
         taxonomiaCategoria.setModeloSentenca(categoria);
         taxonomiaCategoria.setIdModeloSentenca(categoria.getId());
         taxonomiaCategoria.setUsuarioAtualizacao(usuarioAtualizacao);
         this.taxonomiaCategoriaService.save(taxonomiaCategoria);
      }
   }

   private Integer maxOrderByPai(Long idPai) throws CronosException
   {
      return this.getEntityRepository().maxOrderByPai(idPai);
   }

   @Override
   public List<CategoriaItemEntity> listByPartDescription(AutoCompleteDTO autocomplete) throws CronosException
   {
      GrupoTrabalhoEntity grupo = buscaGrupoTrabalho(autocomplete.getIdGrupoTrabalho());
      return getEntityRepository().listByPartDescription(grupo.getId(), autocomplete.getPart(), TipoNodo.I,
               autocomplete.getTipoArvore());
   }

   @Override
   public CategoriaItemEntity alterarIcone(IconeDTO iconeDTO) throws CronosException
   {
      CategoriaItemEntity categoria = get(iconeDTO.getId());
      if (iconeDTO.getImagem() != null)
      {
         IconesPersonalEntity icone = createIconePersonal(iconeDTO, iconeDTO.getImagem());
         icone = this.iconesPersonalService.save(icone);
         categoria.setIconePersonal(icone);
         categoria.setIdIcone(icone.getId());
         categoria.setCor(null);
         categoria.setIcone(null);
      }
      else
      {
         categoria.setCor(iconeDTO.getCor());
         categoria.setIcone(iconeDTO.getIcone());
         categoria.setIdIcone(null);
         categoria.setIconePersonal(null);
      }
      categoria.setUsuarioAtualizacao(iconeDTO.getUsuarioAtualizacao());
      return getEntityRepository().save(categoria);
   }

   private IconesPersonalEntity createIconePersonal(IconeDTO iconeDTO, ImagemDTO imagem)
   {
      IconesPersonalEntity icone = new IconesPersonalEntity();
      icone.setImagem(imagem.getBase64());
      icone.setTipo(imagem.getFiletype());
      icone.setUsuarioAtualizacao(iconeDTO.getUsuarioAtualizacao());
      return icone;
   }

   @Override
   public List<RetornoPesquisaGeralDTO> buscarPesquisaGeral(PesquisaGeralDTO pesquisaGeralDTO) throws CronosException
   {
      GrupoTrabalhoEntity grupoTrabalho = buscaGrupoTrabalho(pesquisaGeralDTO.getIdGrupoTrabalho());

      final String texto = pesquisaGeralDTO.getPart();
      if (texto.contains(ConstantUtil.PERCENT))
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
      TipoArvore tipoArvore = pesquisaGeralDTO.getTipoArvore();
      List<RetornoPesquisaGeralDTO> listaRetorno = new ArrayList<RetornoPesquisaGeralDTO>();

      Long idGrupoTrabalho = grupoTrabalho.getId();
      if (TipoLabel.TU.equals(pesquisaGeralDTO.getTipo()) || TipoLabel.ET.equals(pesquisaGeralDTO.getTipo()))
      {
         carregaEtiqueta(texto, idGrupoTrabalho, tipoArvore, listaRetorno);
      }
      if (TipoLabel.TU.equals(pesquisaGeralDTO.getTipo()) || TipoLabel.EL.equals(pesquisaGeralDTO.getTipo()))
      {
         carregaElementoPesquisaGeral(texto, idGrupoTrabalho, tipoArvore, listaRetorno);
      }
      if (TipoLabel.TU.equals(pesquisaGeralDTO.getTipo()) || TipoLabel.TI.equals(pesquisaGeralDTO.getTipo()))
      {
         carregaCategoria(texto, idGrupoTrabalho, tipoArvore, listaRetorno);
      }
      return listaRetorno;
   }

   private void carregaCategoria(String texto, Long idGrupoTrabalho, TipoArvore tipoArvore,
            List<RetornoPesquisaGeralDTO> listaRetorno) throws CronosException
   {
      List<CategoriaItemEntity> listByPartDescriptionTipoArvore = getEntityRepository().listPesquisaGeral(texto,
               tipoArvore, idGrupoTrabalho);

      for (CategoriaItemEntity categoria : listByPartDescriptionTipoArvore)
      {
         LabelDTO labelDTO = new LabelDTO();
         labelDTO.setTipo(TipoLabel.TI);
         labelDTO.setLabel(categoria.getTitulo());
         boolean naoachou = true;
         for (RetornoPesquisaGeralDTO retorno : listaRetorno)
         {
            if (retorno.getId().longValue() == categoria.getId().longValue())
            {
               naoachou = false;
               retorno.getLabel().add(labelDTO);
               break;
            }
         }
         if (naoachou)
         {
            RetornoPesquisaGeralDTO retorno = createRetornoPesquisaGeral(tipoArvore, categoria, idGrupoTrabalho);
            insereLabel(labelDTO, retorno);
            listaRetorno.add(retorno);
         }
      }
   }

   private void carregaElementoPesquisaGeral(String texto, Long idGrupoTrabalho, TipoArvore tipoArvore,
            List<RetornoPesquisaGeralDTO> listaRetorno) throws CronosException
   {
      List<CategoriaItemEntity> likeByElemento = getEntityRepository().likeByElemento(texto, idGrupoTrabalho,
               tipoArvore);
      for (CategoriaItemEntity categoria : likeByElemento)
      {
         List<LabelDTO> elementos = carregaStringElemento(texto, categoria);
         boolean naoachou = validaExistenciaItem(listaRetorno, categoria, elementos);
         if (naoachou)
         {
            RetornoPesquisaGeralDTO retorno = createRetornoPesquisaGeral(tipoArvore, categoria, idGrupoTrabalho);
            retorno.getLabel().addAll(elementos);
            listaRetorno.add(retorno);
         }
      }
   }

   private List<LabelDTO> carregaStringElemento(String texto, CategoriaItemEntity categoria)
   {
      List<LabelDTO> elementos = new ArrayList<LabelDTO>();
      for (ElementoIGTEntity elemento : categoria.getElementos())
      {
         String textoIn = elemento.getTexto();
         if (textoIn != null && textoIn.toUpperCase().contains(texto.toUpperCase()))
         {
            LabelDTO labelDTO = new LabelDTO();
            labelDTO.setTipo(TipoLabel.EL);
            labelDTO.setLabel(elemento.getTipoElemento().getNomeItem());
            elementos.add(labelDTO);
         }
      }
      return elementos;
   }

   private void carregaEtiqueta(String texto, Long idGrupoTrabalho, TipoArvore tipoArvore,
            List<RetornoPesquisaGeralDTO> listaRetorno) throws CronosException
   {
      List<CategoriaItemEntity> likeByEtiqueta = getEntityRepository().likeByEtiqueta(texto, idGrupoTrabalho,
               tipoArvore);
      for (CategoriaItemEntity categoria : likeByEtiqueta)
      {
         List<LabelDTO> etiquetas = carregaStringEtiqueta(texto, categoria);
         boolean naoachou = validaExistenciaItem(listaRetorno, categoria, etiquetas);
         if (naoachou)
         {
            RetornoPesquisaGeralDTO retorno = createRetornoPesquisaGeral(tipoArvore, categoria, idGrupoTrabalho);
            retorno.getLabel().addAll(etiquetas);
            listaRetorno.add(retorno);
         }
      }
   }

   private boolean validaExistenciaItem(List<RetornoPesquisaGeralDTO> listaRetorno, CategoriaItemEntity categoria,
            List<LabelDTO> labels)
   {
      boolean naoachou = true;
      for (RetornoPesquisaGeralDTO retorno : listaRetorno)
      {
         if (retorno.getId().longValue() == categoria.getId().longValue())
         {
            naoachou = false;
            insereLabels(labels, retorno);
            break;
         }
      }
      return naoachou;
   }

   private List<LabelDTO> carregaStringEtiqueta(String texto, CategoriaItemEntity categoria)
   {
      List<LabelDTO> etiquetas = new ArrayList<LabelDTO>();
      for (TaxonomiaEtiquetaGTEntity taxonomiaEtiqueta : categoria.getTaxonomiasEtiqueta())
      {
         EtiquetaGTEntity etiqueta = taxonomiaEtiqueta.getEtiquetaGT();
         String textoIn = etiqueta.getEtiqueta();
         if (textoIn.toUpperCase().contains(texto.toUpperCase()))
         {
            LabelDTO labelDTO = new LabelDTO();
            labelDTO.setTipo(TipoLabel.ET);
            labelDTO.setLabel(textoIn);
            etiquetas.add(labelDTO);
         }
      }
      return etiquetas;
   }

   private void insereLabel(LabelDTO label, RetornoPesquisaGeralDTO retorno)
   {
      boolean existe = false;
      for (LabelDTO labelIn : retorno.getLabel())
      {
         if (labelIn.getLabel().equalsIgnoreCase(label.getLabel()))
         {
            existe = true;
         }
      }
      if (!existe)
      {
         retorno.getLabel().add(label);
      }
   }

   private void insereLabels(List<LabelDTO> labels, RetornoPesquisaGeralDTO retorno)
   {
      for (LabelDTO labelDTO : labels)
      {
         boolean existe = false;
         for (LabelDTO labelIn : retorno.getLabel())
         {
            if (labelIn.getLabel().equalsIgnoreCase(labelDTO.getLabel()))
            {
               existe = true;
            }
         }
         if (!existe)
         {
            retorno.getLabel().add(labelDTO);
         }
      }
   }

   private RetornoPesquisaGeralDTO createRetornoPesquisaGeral(TipoArvore tipoArvore, CategoriaItemEntity categoria,
            Long idGrupoTrabalho) throws CronosException
   {
      RetornoPesquisaGeralDTO retorno = new RetornoPesquisaGeralDTO();
      retorno.setId(categoria.getId());
      retorno.setTitulo(categoria.getTitulo());
      retorno.setVersao(categoria.getVersao());
      retorno.setArvore(tipoArvore);

      retorno.setRevisar(false);
      if (categoria.getIdGrupoTrabalho().equals(idGrupoTrabalho))
      {
         retorno.setEditar(true);
         retorno.setExcluir(true);
         retorno.setAdicionar(false);
         retorno.setVisualizar(false);
         // regra de revis�o
         UsuarioEntity usuario = buscaUsuario(categoria);
         if (Permissoes.A.equals(usuario.getPermissoes()))
         {
            retorno.setRevisar(true);
         }
      }
      else
      {
         retorno.setEditar(false);
         retorno.setExcluir(false);
         retorno.setAdicionar(true);
         retorno.setVisualizar(true);
      }
      return retorno;
   }

   private UsuarioEntity buscaUsuario(CategoriaItemEntity categoria) throws CronosException
   {
      String usuarioAtualizacao = categoria.getUsuarioAtualizacao();
      UsuarioEntity usuario = null;
      for (Entry<String, UsuarioEntity> entry : mapaUsuario.entrySet())
      {
         String key = entry.getKey();
         if (usuarioAtualizacao.equalsIgnoreCase(key))
         {
            usuario = entry.getValue();
            break;
         }
      }
      if (usuario == null)
      {
         usuario = usuarioService.findByLogin(usuarioAtualizacao);
         mapaUsuario.put(usuarioAtualizacao, usuario);
      }
      return usuario;
   }

   public CategoriaItemEntity cloneItem(CloneCategoriaItemDTO categoria) throws CronosException
   {
      String usuarioAtualizacao = categoria.getUsuarioAtualizacao();
      CategoriaItemEntity categoriaAntiga = getEntityRepository().get(categoria.getId());

      GrupoTrabalhoEntity grupoTrabalho = buscaGrupoTrabalho(categoria.getIdGrupoTrabalho());
      CategoriaItemEntity categoriaPai = findPaiByTipoArvoreGrupoTrabalho(categoriaAntiga.getArvore(),
               grupoTrabalho.getId());

      CategoriaItemEntity categoriaNova = categoriaAntiga.clone(grupoTrabalho, categoriaPai);
      categoriaNova.setUsuarioAtualizacao(usuarioAtualizacao);
      categoriaNova = save(categoriaNova);

      cloneTaxonomiaEtiqueta(usuarioAtualizacao, categoriaAntiga, categoriaNova);
      cloneElementos(usuarioAtualizacao, categoriaAntiga, categoriaNova);
      cloneTaxonomiaCategoria(usuarioAtualizacao, categoriaAntiga, categoriaNova);
      return categoriaNova;
   }

   private GrupoTrabalhoEntity buscaGrupoTrabalho(Long idGrupoTrabalho) throws CronosException
   {
      GrupoTrabalhoEntity grupoTrabalho = this.grupoTrabalhoService.get(idGrupoTrabalho);
      if (grupoTrabalho == null)
      {
         throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
      }
      return grupoTrabalho;
   }

   private void cloneElementos(String usuarioAtualizacao, CategoriaItemEntity categoriaAntiga,
            CategoriaItemEntity categoriaNova) throws CronosException
   {
      List<ElementoIGTEntity> listaElementos = this.elementoIGTService.findAllByCategoria(categoriaAntiga.getId());
      for (ElementoIGTEntity elemento : listaElementos)
      {
         ElementoIGTEntity elementoNovo = elemento.clone(categoriaNova);
         elementoNovo.setUsuarioAtualizacao(usuarioAtualizacao);
         this.elementoIGTService.save(elementoNovo);
      }
   }

   private void cloneTaxonomiaEtiqueta(String usuarioAtualizacao, CategoriaItemEntity categoriaAntiga,
            CategoriaItemEntity categoriaNova) throws CronosException
   {
      List<TaxonomiaEtiquetaGTEntity> listaTaxonomiasEtiquetas = this.taxonomiaEtiquetaGTService
               .findAllByCategoria(categoriaAntiga.getId());
      for (TaxonomiaEtiquetaGTEntity taxonomia : listaTaxonomiasEtiquetas)
      {
         EtiquetaGTEntity etiquetaNova = taxonomia.getEtiquetaGT().clone(categoriaNova.getIdGrupoTrabalho());
         EtiquetaGTEntity etiqueta = null;
         String novaEtiqueta = etiquetaNova.getEtiqueta();
         for (Entry<String, EtiquetaGTEntity> entry : mapaEtiquetas.entrySet())
         {
            if (entry.getKey().equals(novaEtiqueta))
            {
               etiqueta = entry.getValue();
               break;
            }
         }
         if (etiqueta == null)
         {
            etiquetaNova.setUsuarioAtualizacao(usuarioAtualizacao);
            etiqueta = this.etiquetaGTService.save(etiquetaNova);
            mapaEtiquetas.put(novaEtiqueta, etiqueta);
         }
         TaxonomiaEtiquetaGTEntity taxonomiaNova = taxonomia.clone(categoriaNova, etiqueta);
         taxonomiaNova.setUsuarioAtualizacao(usuarioAtualizacao);
         this.taxonomiaEtiquetaGTService.save(taxonomiaNova);
      }
   }

   private void cloneTaxonomiaCategoria(String usuarioAtualizacao, CategoriaItemEntity modeloAntigo,
            CategoriaItemEntity modeloNovo) throws CronosException
   {
      List<TaxonomiaCategoriaEntity> listaTaxonomias = this.taxonomiaCategoriaService.findAllByModelo(modeloAntigo
               .getId());
      for (TaxonomiaCategoriaEntity taxonomiaCategoria : listaTaxonomias)
      {
         CategoriaItemEntity categoriaAntiga = taxonomiaCategoria.getCategoriaItem();
         Long idCategoriaOld = categoriaAntiga.getId();
         CategoriaItemEntity categoriaInserida = retornaCategoriaInserida(idCategoriaOld);
         TaxonomiaCategoriaEntity taxonomiaCategoriaNova = taxonomiaCategoria.clone(modeloNovo, categoriaInserida);
         taxonomiaCategoriaNova.setUsuarioAtualizacao(usuarioAtualizacao);
         this.taxonomiaCategoriaService.save(taxonomiaCategoriaNova);
      }
   }

   @Override
   public List<CategoriaItemEntity> listNodeByPartDescription(AutoCompleteDTO autocomplete) throws CronosException
   {
      GrupoTrabalhoEntity grupo = buscaGrupoTrabalho(autocomplete.getIdGrupoTrabalho());
      return getEntityRepository().listByPartDescription(grupo.getId(), autocomplete.getPart(), TipoNodo.N,
               autocomplete.getTipoArvore());
   }

   @Override
   public CategoriaItemEntity saveNode(SaveNodeDTO node) throws CronosException
   {
      GrupoTrabalhoEntity grupo = buscaGrupoTrabalho(node.getIdGrupoTrabalho());
      CategoriaItemEntity categoriaPai = findPaiByTipoArvoreGrupoTrabalho(node.getTipoArvore(), grupo.getId());

      CategoriaItemEntity categoria = new CategoriaItemEntity();
      categoria.setTitulo(node.getTitulo());
      categoria.setIdGrupoTrabalho(grupo.getId());
      categoria.setUsuarioAtualizacao(node.getUsuarioAtualizacao());
      categoria.setIdPai(categoriaPai.getId());
      categoria.setArvore(node.getTipoArvore());
      categoria.setTipo(TipoNodo.N);
      categoria.setCompartilhar(SimNao.N);
      categoria.setTipoCategoria(carregaTipoCategoria(this.tipoCategoriaService.findAll(), TipoNodo.I));
      Integer ordemmax = maxOrderByPai(categoriaPai.getId());
      categoria.setOrdem((ordemmax == null ? 0 : ordemmax) + 1);
      return save(categoria);
   }

   @Override
   public List<CategoriaItemEntity> findAllByGrupoTrabalhoTipoArvore(Long idGrupoTrabalho, TipoArvore tipoArvore)
            throws CronosException
   {
      return getEntityRepository().findAllByGrupoTrabalho(idGrupoTrabalho, tipoArvore);
   }

   @Override
   public List<CategoriaItemEntity> listTopicosByIdModelo(Long id) throws CronosException
   {
      List<CategoriaItemEntity> listaTopicos = new ArrayList<CategoriaItemEntity>();
      try
      {
         List<TaxonomiaCategoriaEntity> listaTaxonomia = this.taxonomiaCategoriaService.findAllByModelo(id);
         for (TaxonomiaCategoriaEntity taxCat : listaTaxonomia)
         {
            listaTopicos.add(taxCat.getCategoriaItem());
         }
         return listaTopicos;
      }
      catch (CronosException e)
      {
         return null;
      }
   }
}
