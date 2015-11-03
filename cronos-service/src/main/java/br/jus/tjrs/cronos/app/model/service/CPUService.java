package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import br.jus.tjrs.cronos.app.model.dto.ProcessoDTO;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.ws.client.wscpu.ProcessoDto;

public interface CPUService
{
   ProcessoDTO carregarProcesso(String numeroProcesso) throws CronosException;

   List<ProcessoDto> listaProcessos(String perfil, String numeroProcesso, String comarca) throws Exception;

   List<ProcessoDto> buscaProcessosPorNumeroDoProcesso(String perfil, String numeroProcesso, String codComarca)
            throws Exception;
}
