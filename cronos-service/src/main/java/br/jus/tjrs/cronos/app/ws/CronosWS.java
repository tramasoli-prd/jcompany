package br.jus.tjrs.cronos.app.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.jus.tjrs.cronos.core.CronosException;

@WebService
public interface CronosWS
{
   @WebMethod
   String exportarSentenca(String cnj) throws CronosException;
   
   @WebMethod
   String buscaComarca(Integer idComarca) throws CronosException;

}
