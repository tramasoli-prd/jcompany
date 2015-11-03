package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.dto.AutoCompleteDTO;
import br.jus.tjrs.cronos.app.model.dto.CarregarCategoriaDTO;
import br.jus.tjrs.cronos.app.model.dto.CategoriaItemDTO;
import br.jus.tjrs.cronos.app.model.dto.CloneCategoriaItemDTO;
import br.jus.tjrs.cronos.app.model.dto.IconeDTO;
import br.jus.tjrs.cronos.app.model.dto.PesquisaGeralDTO;
import br.jus.tjrs.cronos.app.model.dto.RetornoPesquisaGeralDTO;
import br.jus.tjrs.cronos.app.model.dto.SaveNodeDTO;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.model.entity.GrupoTrabalhoEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface CategoriaItemService extends EntityService<Long, CategoriaItemEntity>
{
   CategoriaItemDTO carregarCategoria(CarregarCategoriaDTO carregarCategoria) throws CronosException;

   List<CategoriaItemEntity> findAllByPai(Long idCategoria) throws CronosException;

   List<CategoriaItemEntity> findAllByGrupoTrabalho(Long idGrupoTrabalho) throws CronosException;

   CategoriaItemEntity salvarTopico(CategoriaItemDTO categoriaItemDTO) throws CronosException;

   List<CategoriaItemEntity> listByPartDescription(AutoCompleteDTO autocomplete) throws CronosException;

   CategoriaItemEntity alterarIcone(IconeDTO icone) throws CronosException;

   List<RetornoPesquisaGeralDTO> buscarPesquisaGeral(PesquisaGeralDTO part) throws CronosException;

   CategoriaItemEntity cloneItem(CloneCategoriaItemDTO categoria) throws CronosException;

   List<CategoriaItemEntity> listNodeByPartDescription(AutoCompleteDTO autocomplete) throws CronosException;

   CategoriaItemEntity saveNode(SaveNodeDTO node) throws CronosException;

   List<CategoriaItemEntity> findAllByGrupoTrabalhoTipoArvore(Long id, TipoArvore tipoArvore) throws CronosException;

   void createFirstTreeView(GrupoTrabalhoEntity grupoTrabalhoNovo, GrupoTrabalhoEntity grupoTrabalhoAntigo)
            throws CronosException;

   List<CategoriaItemEntity> listTopicosByIdModelo(Long id) throws CronosException;
}
