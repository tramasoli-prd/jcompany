package br.jus.tjrs.cronos.app.model.service;

import br.jus.tjrs.cronos.core.CronosException;

public interface ComarcaService
{

   String buscaComarca(Integer idComarca) throws CronosException;

   String buscaComarca(String codigoComarca) throws CronosException;

}
