package br.jus.tjrs.cronos.app.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.dto.UsuarioCTJDTO;
import br.jus.tjrs.cronos.core.CronosException;
//import br.jus.tjrs.ws.client.ctj.CtjMessage;
//import br.jus.tjrs.ws.client.ctj.CtjService;
//import br.jus.tjrs.ws.client.ctj.CtjWsError_Exception;
//import br.jus.tjrs.ws.client.ctj.CtjWsFacade;
//import br.jus.tjrs.ws.client.ctj.RetornoListaPessoas;
//import br.jus.tjrs.ws.client.ctj.RetornoPessoaDTO;

public class UsuarioCTJServiceImpl implements UsuarioCTJService
{

   public List<UsuarioCTJDTO> listaPessoas(String parteNome) throws CronosException
   {
//      CtjWsFacade ctj = new CtjService().getCtjPort();
//      try
//      {
//         RetornoListaPessoas retornoListaPessoas = ctj.listaPessoas(null, SimNao.S.toString(), null, parteNome, null,
//                  null);
//         List<UsuarioCTJDTO> lista = new ArrayList<UsuarioCTJDTO>();
//         for (RetornoPessoaDTO pessoaDTO : retornoListaPessoas.getLista())
//         {
//            if (br.jus.tjrs.ws.client.ctj.SimNao.S.equals(pessoaDTO.getMagistrado()))
//            {
//               continue;
//            }
//            UsuarioCTJDTO usuario = new UsuarioCTJDTO();
//            usuario.setCpf(pessoaDTO.getCpf());
//            usuario.setNome(pessoaDTO.getNome());
//            usuario.setMagistrado(SimNao.N);
//            lista.add(usuario);
//         }
//         Collections.sort(lista, new Comparator<UsuarioCTJDTO>()
//         {
//            @Override
//            public int compare(UsuarioCTJDTO obj1, UsuarioCTJDTO obj2)
//            {
//               return obj1.getNome().compareTo(obj2.getNome());
//            }
//         });
//         return lista;
//      }
//      catch (CtjWsError_Exception e)
//      {
//         if (e.getFaultInfo() != null && CtjMessage.ERROR_CTJ_LISTA_VAZIA_PESSOAS.equals(e.getFaultInfo().getError()))
//         {
//            throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
//         }
//         throw CronosErrors.FALHA_SOLICITACAO_002.create();
//      }
	   return null;
   }
}
