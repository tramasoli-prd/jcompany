package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.dto.ElementoDocumentoDTO;
import br.jus.tjrs.cronos.app.model.dto.GrupoElementoDocumentoDTO;
import br.jus.tjrs.cronos.app.model.dto.ParteDTO;
import br.jus.tjrs.cronos.app.model.dto.ProcessoDTO;
import br.jus.tjrs.cronos.app.model.dto.SentencaDTO;
import br.jus.tjrs.cronos.app.model.entity.AlegacaoGTEntity;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.model.entity.SentencaEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaSentencaEntity;
import br.jus.tjrs.cronos.app.model.entity.TipoAlegacaoEntity;
import br.jus.tjrs.cronos.app.model.entity.TokenEntity;
import br.jus.tjrs.cronos.app.model.repository.SentencaRepository;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.app.util.HTMLUtil;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class SentencaServiceImpl extends AbstractServiceEntity<Long, SentencaEntity> implements SentencaService
{

   @Inject
   private SentencaRepository sentencaRepository;

   @Inject
   private TaxonomiaSentencaService taxonomiaSentencaService;

   @Inject
   private ElementoIGTService elementoService;

   @Inject
   private AlegacaoGTService alegacaoGTService;

   @Inject
   private TokenService tokenService;

   @Inject
   private CPUService cpuService;

   @Inject
   private ComarcaService comarcaService;

   @Override
   protected SentencaRepository getEntityRepository()
   {
      return sentencaRepository;
   }

   public SentencaDTO carregarSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      if (sentencaDTO.getId() != null)
      {
         SentencaEntity sentenca = getEntityRepository().findByNumeroProcessoFinalizada(
                  sentencaDTO.getNumeroProcesso(), sentencaDTO.getFinalizada());
         sentencaDTO.setSentencaGerada(sentenca.getSentencaPronta());
         sentencaDTO.setFinalizada(sentenca.getFinalizada());
         sentencaDTO.setId(sentenca.getId());
         // --
         List<TaxonomiaSentencaEntity> taxonomiaSentencas = carregarTaxonomiaSentenca(sentenca.getId());
         sentencaDTO.setModelo(carregaModelo(taxonomiaSentencas));
         sentencaDTO.setTopicos(carregaTopicos(taxonomiaSentencas));
      }
      return sentencaDTO;
   }

   public SentencaDTO carregarSentencaId(SentencaDTO sentencaDTO) throws CronosException
   {
      if (sentencaDTO.getId() != null)
      {
         SentencaEntity sentenca = getEntityRepository().get(sentencaDTO.getId());
         if (sentenca != null)
         {
            sentencaDTO.setSentencaGerada(sentenca.getSentencaPronta());
            sentencaDTO.setFinalizada(sentenca.getFinalizada());
            sentencaDTO.setId(sentenca.getId());
            sentencaDTO.setNumeroProcesso(sentenca.getNumeroProcesso());
            ProcessoDTO processo = cpuService.carregarProcesso(sentenca.getNumeroProcesso());
            processo.setComarca(this.comarcaService.buscaComarca(processo.getCodigoComarca()));
            sentencaDTO.setDadosProcesso(processo);
            // --
            List<TaxonomiaSentencaEntity> taxonomiaSentencas = carregarTaxonomiaSentenca(sentenca.getId());
            sentencaDTO.setModelo(carregaModelo(taxonomiaSentencas));
            sentencaDTO.setTopicos(carregaTopicos(taxonomiaSentencas));
         }
      }
      return sentencaDTO;
   }

   public SentencaDTO salvarSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      CategoriaItemEntity modelo = null;
      if (sentencaDTO.getModelo() != null && sentencaDTO.getModelo().getId() != null)
      {
         modelo = sentencaDTO.getModelo();
      }
      List<CategoriaItemEntity> topicos = sentencaDTO.getTopicos();
      // --
      SentencaEntity sentenca;
      boolean nova = false;
      if (sentencaDTO.getId() != null)
      {
         sentenca = get(sentencaDTO.getId());
         nova = false;
      }
      else
      {
         sentenca = new SentencaEntity();
         sentenca.setIdGrupoTrabalho(sentencaDTO.getIdGrupoTrabalho());
         Integer ordemmax = maxOrderByFinalizada(SimNao.N);
         sentenca.setOrdem((ordemmax == null ? 0 : ordemmax) + 1);

         nova = true;
      }
      sentenca.setFinalizada(sentencaDTO.getFinalizada());
      sentenca.setNumeroProcesso(sentencaDTO.getNumeroProcesso());
      sentenca.setUsuarioAtualizacao(sentencaDTO.getUsuarioAtualizacao());

      String documentoGerado = gerarDocumentoSentenca(sentenca, modelo, topicos);

      sentenca.setSentencaPronta(documentoGerado);
      sentenca = save(sentenca);
      // --
      sentencaDTO.setSentencaGerada(sentenca.getSentencaPronta());
      sentencaDTO.setId(sentenca.getId());
      // --
      List<TaxonomiaSentencaEntity> taxonomiaSentencas = carregarTaxonomiaSentenca(sentenca.getId());
      if (!nova)
      {
         removerModelo(modelo, taxonomiaSentencas, sentencaDTO.getUsuarioAtualizacao());
         removerTopicos(topicos, taxonomiaSentencas, sentencaDTO.getUsuarioAtualizacao());
      }
      salvarModelo(modelo, taxonomiaSentencas, sentenca, sentencaDTO.getUsuarioAtualizacao());
      salvarTopicos(topicos, taxonomiaSentencas, sentenca, sentencaDTO.getUsuarioAtualizacao());
      // --
      return sentencaDTO;
   }

   private void removerModelo(CategoriaItemEntity modelo, List<TaxonomiaSentencaEntity> taxonomiaSentencas,
            String usuarioAtualizacao) throws CronosException
   {
      for (TaxonomiaSentencaEntity taxonomiaSentencaAntiga : taxonomiaSentencas)
      {
         CategoriaItemEntity categoria = taxonomiaSentencaAntiga.getCategoriaItem();
         if (TipoArvore.M.equals(categoria.getArvore()))
         {
            if (modelo == null)
            {
               taxonomiaSentencaAntiga.setUsuarioAtualizacao(usuarioAtualizacao);
               this.taxonomiaSentencaService.remove(taxonomiaSentencaAntiga);
               break;
            }
            else if (categoria.getId().longValue() != modelo.getId().longValue())
            {
               taxonomiaSentencaAntiga.setUsuarioAtualizacao(usuarioAtualizacao);
               this.taxonomiaSentencaService.remove(taxonomiaSentencaAntiga);
               break;
            }
         }
      }
   }

   private void removerTopicos(List<CategoriaItemEntity> topicos, List<TaxonomiaSentencaEntity> taxonomiaSentencas,
            String usuarioAtualizacao) throws CronosException
   {
      if (topicos == null || topicos.isEmpty())
      {
         for (TaxonomiaSentencaEntity taxonomiaSentencaAntiga : taxonomiaSentencas)
         {
            CategoriaItemEntity categoria = taxonomiaSentencaAntiga.getCategoriaItem();
            if (TipoArvore.T.equals(categoria.getArvore()))
            {
               taxonomiaSentencaAntiga.setUsuarioAtualizacao(usuarioAtualizacao);
               this.taxonomiaSentencaService.remove(taxonomiaSentencaAntiga);
            }
         }
      }
      else
      {
         for (TaxonomiaSentencaEntity taxonomiaSentencaAntiga : taxonomiaSentencas)
         {
            CategoriaItemEntity categoria = taxonomiaSentencaAntiga.getCategoriaItem();
            if (TipoArvore.T.equals(categoria.getArvore()))
            {
               boolean existe = false;
               for (CategoriaItemEntity topicoNovo : topicos)
               {
                  if (categoria.getId().longValue() == topicoNovo.getId().longValue())
                  {
                     existe = true;
                  }
               }
               if (!existe)
               {
                  taxonomiaSentencaAntiga.setUsuarioAtualizacao(usuarioAtualizacao);
                  this.taxonomiaSentencaService.remove(taxonomiaSentencaAntiga);
               }
            }
         }
      }
   }

   private void salvarTopicos(List<CategoriaItemEntity> topicos, List<TaxonomiaSentencaEntity> taxonomiaSentencas,
            SentencaEntity sentenca, String usuarioAtualizacao)
            throws CronosException
   {
      int ordem = 0;
      for (CategoriaItemEntity topicoNovo : topicos)
      {
         ordem++;
         boolean existe = false;
         for (TaxonomiaSentencaEntity taxonomiaSentencaAntiga : taxonomiaSentencas)
         {
            CategoriaItemEntity categoria = taxonomiaSentencaAntiga.getCategoriaItem();
            if (TipoArvore.T.equals(categoria.getArvore()))
            {
               if (categoria.getId().longValue() == topicoNovo.getId().longValue())
               {
                  existe = true;
               }
            }
         }
         if (existe)
         {
            continue;
         }
         TaxonomiaSentencaEntity taxonomiaSentenca = new TaxonomiaSentencaEntity();
         taxonomiaSentenca.setIdSentenca(sentenca.getId());
         taxonomiaSentenca.setOrdem(ordem);
         taxonomiaSentenca.setIdCategoria(topicoNovo.getId());
         taxonomiaSentenca.setUsuarioAtualizacao(usuarioAtualizacao);
         this.taxonomiaSentencaService.save(taxonomiaSentenca);
      }
   }

   private void salvarModelo(CategoriaItemEntity modelo, List<TaxonomiaSentencaEntity> taxonomiaSentencas,
            SentencaEntity sentenca, String usuarioAtualizacao)
            throws CronosException
   {
      boolean existe = false;
      for (TaxonomiaSentencaEntity taxonomiaSentencaAntiga : taxonomiaSentencas)
      {
         CategoriaItemEntity categoria = taxonomiaSentencaAntiga.getCategoriaItem();
         if (TipoArvore.M.equals(categoria.getArvore()))
         {
            if (categoria.getId().longValue() == modelo.getId().longValue())
            {
               existe = true;
               break;
            }
         }
      }
      if (!existe)
      {
         TaxonomiaSentencaEntity taxonomiaSentenca = new TaxonomiaSentencaEntity();
         taxonomiaSentenca.setIdSentenca(sentenca.getId());
         taxonomiaSentenca.setIdCategoria(modelo.getId());
         taxonomiaSentenca.setUsuarioAtualizacao(usuarioAtualizacao);
         taxonomiaSentenca.setOrdem(1);
         this.taxonomiaSentencaService.save(taxonomiaSentenca);
      }
   }

   private List<CategoriaItemEntity> carregaTopicos(List<TaxonomiaSentencaEntity> taxonomiaSentencas)
   {
      List<CategoriaItemEntity> listaTopicos = new ArrayList<CategoriaItemEntity>();
      for (TaxonomiaSentencaEntity taxonomiaSentencaEntity : taxonomiaSentencas)
      {
         CategoriaItemEntity categoria = taxonomiaSentencaEntity.getCategoriaItem();
         if (TipoArvore.T.equals(categoria.getArvore()))
         {
            listaTopicos.add(categoria);
         }
      }
      return listaTopicos;
   }

   private CategoriaItemEntity carregaModelo(List<TaxonomiaSentencaEntity> taxonomiaSentencas)
   {
      for (TaxonomiaSentencaEntity taxonomiaSentencaEntity : taxonomiaSentencas)
      {
         CategoriaItemEntity categoria = taxonomiaSentencaEntity.getCategoriaItem();
         if (TipoArvore.M.equals(categoria.getArvore()))
         {
            return categoria;
         }
      }
      return null;
   }

   @Override
   public SentencaEntity findByNumeroProcessoFinalizada(String numeroProcesso, SimNao finalizada)
            throws CronosException
   {
      return getEntityRepository().findByNumeroProcessoFinalizada(numeroProcesso, finalizada);
   }

   @Override
   public List<SentencaEntity> listByGrupoTrabalhoFinalizada(Long idGrupoTrabalho, SimNao fizalizada)
            throws CronosException
   {
      return getEntityRepository().listByGrupoTrabalhoFinalizada(idGrupoTrabalho, fizalizada);
   }

   public SentencaDTO finalizarSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      SentencaEntity sentenca = get(sentencaDTO.getId());
      sentenca.setSentencaPronta(sentencaDTO.getSentencaGerada());
      sentenca.setFinalizada(SimNao.S);
      getEntityRepository().save(sentenca);
      return sentencaDTO;
   }

   public boolean removerSentenca(SentencaDTO sentencaDTO) throws CronosException
   {
      SentencaEntity sentenca = get(sentencaDTO.getId());
      List<TaxonomiaSentencaEntity> listaSentencas = this.taxonomiaSentencaService.findAllBySentenca(sentenca
               .getId());
      for (TaxonomiaSentencaEntity taxonomiaSentencaEntity : listaSentencas)
      {
         this.taxonomiaSentencaService.remove(taxonomiaSentencaEntity);
      }
      remove(sentenca);
      return true;
   }

   public SentencaEntity findByStatusFinalizada(SentencaDTO sentencaDTO) throws CronosException
   {
      return getEntityRepository().findByNumeroProcessoFinalizada(sentencaDTO.getNumeroProcesso(), SimNao.S);
   }

   private String gerarDocumentoSentenca(SentencaEntity sentenca, CategoriaItemEntity modelo,
            List<CategoriaItemEntity> listaTopicos) throws CronosException
   {
      StringBuilder sentencaGerada = new StringBuilder();
      List<GrupoElementoDocumentoDTO> listaElementos = new ArrayList<GrupoElementoDocumentoDTO>();
      int ordemGrupo = 1;
      if (modelo != null)
      {
         ordemGrupo = geraOrdemGrupo(modelo, listaElementos, ordemGrupo);
      }
      for (CategoriaItemEntity topico : listaTopicos)
      {
         ordemGrupo = geraOrdemGrupo(topico, listaElementos, ordemGrupo);
      }

      final String fundamento = "Fundamento";
      final String ementa = "Ementa";

      for (GrupoElementoDocumentoDTO grupo : listaElementos)
      {
         String tipoElemento = grupo.getTipoElemento();
         for (ElementoDocumentoDTO elemento : grupo.getElemento())
         {
            if (fundamento.equalsIgnoreCase(tipoElemento) && elemento.getTitulo() != null)
            {
               sentencaGerada.append(HTMLUtil.P_CENTER_B).append(elemento.getTitulo()).append(HTMLUtil.P_E);
               sentencaGerada.append(HTMLUtil.BR);
            }
            String texto = elemento.getTexto();
            if (ementa.equalsIgnoreCase(tipoElemento) && elemento.getRecuo() > 0)
            {
               int recuo = elemento.getRecuo() / 4;
               texto = adicionaRecuoEmenta(recuo, texto);
            }
            sentencaGerada.append(texto);
            sentencaGerada.append(HTMLUtil.BR);
         }
      }
      String sentencaS = sentencaGerada.toString();

      List<AlegacaoGTEntity> alegacao = carregarAlegacao(sentenca);
      for (AlegacaoGTEntity alegacaoGTEntity : alegacao)
      {
         TipoAlegacaoEntity tipoAlegacao = alegacaoGTEntity.getTipoAlegacao();
         if (tipoAlegacao.getLabel() != null && !tipoAlegacao.getLabel().isEmpty())
         {
            String tag = substituiColchete(tipoAlegacao.getLabel());
            sentencaS = sentencaS.replaceAll(tag, alegacaoGTEntity.getTextoAlegacao());
         }
      }
      List<TokenEntity> tokens = carregarToken();
      Map<String, String> mapaToken = carregarToken(sentenca.getNumeroProcesso());

      for (TokenEntity tokenEntity : tokens)
      {
         if (tokenEntity.getTabela().startsWith(ConstantUtil.WS_CPU))
         {
            final String valor = mapaToken.get(tokenEntity.getCampo());

            String tag = substituiColchete(tokenEntity.getLabel());
            sentencaS = sentencaS.replaceAll(tag, valor != null ? valor : ConstantUtil.EMPTY);
         }
      }
      return sentencaS;
   }

   private int geraOrdemGrupo(CategoriaItemEntity categoria, List<GrupoElementoDocumentoDTO> listaElementos,
            int ordemGrupo)
            throws CronosException
   {
      List<ElementoIGTEntity> elementosModelos = elementoService.findAllByCategoria(categoria.getId());
      return gerarGrupoElementoDocumento(listaElementos, ordemGrupo, elementosModelos);
   }

   private String substituiColchete(final String label)
   {
      return label.replace(ConstantUtil.BRACKET_LEFT, "\\[").replace(ConstantUtil.BRACKET_RIGHT, "\\]");
   }

   private Map<String, String> carregarToken(String numeroProcesso) throws CronosException
   {
      Map<String, String> mapaProcesso = new HashMap<String, String>();
      final String outros = " e Outros";
      ProcessoDTO processo = cpuService.carregarProcesso(numeroProcesso);
      processo.setComarca(this.comarcaService.buscaComarca(processo.getCodigoComarca()));
      // --
      String vara = processo.getVara();
      StringBuilder autor = new StringBuilder(processo.getAutor().size());
      StringBuilder autorOutros = new StringBuilder(processo.getAutor().size());
      boolean primeiro = true;
      for (ParteDTO parte : processo.getAutor())
      {
         if (primeiro)
         {
            autor.append(parte.getNome());
            autorOutros.append(parte.getNome());
            primeiro = false;
            continue;
         }
         autor.append(ConstantUtil.COMMA).append(parte.getNome());
      }
      autorOutros.append(outros);
      StringBuilder reuTodos = new StringBuilder(processo.getReu().size());
      StringBuilder reuOutros = new StringBuilder(processo.getReu().size());
      primeiro = true;
      for (ParteDTO parte : processo.getReu())
      {
         if (primeiro)
         {
            reuTodos.append(parte.getNome());
            reuOutros.append(parte.getNome());
            primeiro = false;
            continue;
         }
         reuTodos.append(ConstantUtil.COMMA).append(parte.getNome());
      }
      reuOutros.append(outros);
      // --
      mapaProcesso.put("classeCNJ", processo.getClasseCNJ());
      mapaProcesso.put("numeroCNJ", processo.getNumeroCNJ());
      mapaProcesso.put("vara", vara);
      mapaProcesso.put("comarca", processo.getComarca());
      mapaProcesso.put("juizado", processo.getJuizado());
      mapaProcesso.put("autorTodos", autor.toString());
      mapaProcesso.put("autorOutros", autor.toString());
      mapaProcesso.put("reuTodos", reuTodos.toString());
      mapaProcesso.put("reuOutros", reuOutros.toString());
      mapaProcesso.put("assuntoCNJ", processo.getAssuntoCNJ());
      mapaProcesso.put("valorAcao", processo.getValorAcao());
      return mapaProcesso;
   }

   private String adicionaRecuoEmenta(int recuo, String texto)
   {
      final StringBuilder recuoAnt = new StringBuilder(HTMLUtil.BLOCKQUOTE_EMENTA_B.length() * recuo);
      for (int iRecuo = 0; iRecuo < recuo; iRecuo++)
      {
         recuoAnt.append(HTMLUtil.BLOCKQUOTE_EMENTA_B);
      }
      final StringBuilder recuoDep = new StringBuilder(HTMLUtil.BLOCKQUOTE_EMENTA_B.length() * recuo);
      for (int iRecuo = 0; iRecuo < recuo; iRecuo++)
      {
         recuoDep.append(HTMLUtil.BLOCKQUOTE_E);
      }
      return recuoAnt.toString() + texto + recuoDep.toString();
   }

   private int gerarGrupoElementoDocumento(List<GrupoElementoDocumentoDTO> listaElementos, int ordemGrupo,
            List<ElementoIGTEntity> elementosModelos)
   {
      for (ElementoIGTEntity elementoIGTEntity : elementosModelos)
      {
         if (elementoIGTEntity.getTexto() == null)
         {
            continue;
         }
         ElementoDocumentoDTO elementoDoc = new ElementoDocumentoDTO();
         elementoDoc.setTitulo(elementoIGTEntity.getTitulo());
         elementoDoc.setTexto(elementoIGTEntity.getTexto());
         elementoDoc.setRecuo(elementoIGTEntity.getTipoElemento().getRecuo());

         final String tipoElemento = elementoIGTEntity.getTipoElemento().getNomeItem();
         GrupoElementoDocumentoDTO grupoDocumento = carregaGrupoElementoDocumento(listaElementos, tipoElemento);
         if (grupoDocumento == null)
         {
            grupoDocumento = new GrupoElementoDocumentoDTO();
            grupoDocumento.setOrdem(ordemGrupo);
            grupoDocumento.setTipoElemento(tipoElemento);
            listaElementos.add(grupoDocumento);
         }
         elementoDoc.setOrdem((grupoDocumento.getElemento().size() + 1));
         grupoDocumento.getElemento().add(elementoDoc);
         ordemGrupo++;
      }
      return ordemGrupo;
   }

   private GrupoElementoDocumentoDTO carregaGrupoElementoDocumento(List<GrupoElementoDocumentoDTO> listaElementos,
            String tipoElemento)
   {
      final String texto = "TEXTO";
      final String voto = "VOTO";
      for (GrupoElementoDocumentoDTO grupo : listaElementos)
      {
         if (grupo.getTipoElemento().equals(tipoElemento)
                  && !tipoElemento.toUpperCase().contains(texto) && !tipoElemento.toUpperCase().contains(voto))
         {
            return grupo;
         }
      }
      return null;
   }

   private List<TaxonomiaSentencaEntity> carregarTaxonomiaSentenca(Long idSentenca) throws CronosException
   {
      try
      {

         return taxonomiaSentencaService.findAllBySentenca(idSentenca);
      }
      catch (CronosException e)
      {
         return null;
      }
   }

   private List<TokenEntity> carregarToken() throws CronosException
   {
      return tokenService.findAll();
   }

   private List<AlegacaoGTEntity> carregarAlegacao(SentencaEntity sentenca) throws CronosException
   {
      return alegacaoGTService.findAllByGrupoTrabalho(sentenca.getIdGrupoTrabalho());
   }

   @Override
   public String exportarSentenca(String numeroProcesso) throws CronosException
   {
      SentencaEntity sentenca = findByNumeroProcessoFinalizada(numeroProcesso, SimNao.S);
      if (sentenca != null)
      {
         return sentenca.getSentencaPronta();
      }
      return null;
   }

   private Integer maxOrderByFinalizada(SimNao finalizada) throws CronosException
   {
      return this.getEntityRepository().maxOrderByFinalizada(finalizada);
   }

   @Override
   public String exportarSentenca(Long id) throws CronosException
   {
      SentencaEntity sentenca = get(id);
      if (sentenca != null)
      {
         return sentenca.getSentencaPronta();
      }
      return null;
   }

}
