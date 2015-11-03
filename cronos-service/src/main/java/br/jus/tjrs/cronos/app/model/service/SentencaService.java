package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.dto.SentencaDTO;
import br.jus.tjrs.cronos.app.model.entity.SentencaEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface SentencaService extends EntityService<Long, SentencaEntity>
{

   SentencaDTO finalizarSentenca(SentencaDTO sentencaDTO) throws CronosException;

   String exportarSentenca(String numeroProcesso) throws CronosException;

   String exportarSentenca(Long id) throws CronosException;

   SentencaEntity findByNumeroProcessoFinalizada(String numeroProcesso, SimNao fizalizada) throws CronosException;

   SentencaDTO carregarSentenca(SentencaDTO sentencaDTO) throws CronosException;

   List<SentencaEntity> listByGrupoTrabalhoFinalizada(Long idGrupoTrabalho, SimNao fizalizada) throws CronosException;

   SentencaDTO carregarSentencaId(SentencaDTO sentencaDTO) throws CronosException;

   SentencaDTO salvarSentenca(SentencaDTO sentencaDTO) throws CronosException;

   boolean removerSentenca(SentencaDTO sentencaDTO) throws CronosException;

   SentencaEntity findByStatusFinalizada(SentencaDTO sentencaDTO) throws CronosException;

}