package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.model.domain.TipoParte;
import br.jus.tjrs.cronos.app.model.dto.ParteDTO;
import br.jus.tjrs.cronos.app.model.dto.ProcessoDTO;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;
//import br.jus.tjrs.ws.client.wscpu.BusinessException;
import br.jus.tjrs.ws.client.wscpu.ParteDto;
import br.jus.tjrs.ws.client.wscpu.ProcessoDto;
import br.jus.tjrs.ws.client.wscpu.ProcessoDto.Partes;
//import br.jus.tjrs.ws.client.wscpu.WSCpuFacade;
//import br.jus.tjrs.ws.client.wscpu.util.CpuClientWebServiceFactory;

public class CPUServiceImpl implements CPUService
{

   public List<ProcessoDto> listaProcessos(String perfil, String numeroProcesso, String comarca) throws Exception
   {
//      WSCpuFacade cpuFacade = autenticarCPU();
//      try
//      {
//         return cpuFacade.listaProcesso(perfil, comarca, numeroProcesso);
//      }
//      catch (Exception e)
//      {
//         e.printStackTrace();
//         throw e;
//      }
	   return null;
   }

   public ProcessoDTO carregarProcesso(String numeroProcesso) throws CronosException
   {
//      try
//      {
//         WSCpuFacade cpuFacade = autenticarCPU();
//
//         String numero = numeroProcesso;
//         String comarca = null;
//
//         if (numeroProcesso.length() < 20)
//         {
//            comarca = numeroProcesso.substring(0, 3);
//            numero = numeroProcesso.substring(3);
//         }
//
//         List<ProcessoDto> lista = cpuFacade.listaProcesso(ConstantUtil.PERFIL_CPU, comarca, numero);
//         if (lista != null)
//         {
//            ProcessoDto processoCPU = lista.get(0);
//            ProcessoDTO dto = new ProcessoDTO();
//            dto.setNumeroCNJ(processoCPU.getNumeroCNJ());
//            dto.setNumeroFormatado(processoCPU.getNumeroFormatado());
//            dto.setClasseCNJ(processoCPU.getClasseCNJ());
//            dto.setAssuntoCNJ(processoCPU.getAssuntoCNJ());
//            dto.setVara(processoCPU.getVara());
//            dto.setJuizado(processoCPU.getJuizado());
//            dto.setCodigoComarca(processoCPU.getCodigoComarca());
//            dto.setValorAcao(processoCPU.getValorDaAcao());
//            Partes partes = processoCPU.getPartes();
//            for (ParteDto parte : partes.getParte())
//            {
//               if (TipoParte.R.name().equalsIgnoreCase(parte.getCodigoTipo()))
//               {
//                  ParteDTO parteNova = createPartes(dto.getReu(), parte, TipoParte.R);
//                  if (parteNova != null)
//                  {
//                     dto.getReu().add(parteNova);
//                  }
//               }
//               else
//               {
//                  ParteDTO parteNova = createPartes(dto.getAutor(), parte, TipoParte.A);
//                  if (parteNova != null)
//                  {
//                     dto.getAutor().add(parteNova);
//                  }
//               }
//            }
//            return dto;
//         }
//         else
//         {
//            throw CronosErrors.REGISTROS_NAO_ENCONTADOS_013.create();
//         }
//      }
//      catch (BusinessException e)
//      {
//         throw CronosErrors.FALHA_SOLICITACAO_024.create();
//      }
//      catch (Exception e)
//      {
//         throw CronosErrors.FALHA_OPERACAO_009.create();
//      }
	   return null;
   }

   private ParteDTO createPartes(List<ParteDTO> lista, ParteDto parte, TipoParte tipoParte)
   {
      for (ParteDTO parteDTO : lista)
      {
         if (parte.getNome().equalsIgnoreCase(parteDTO.getNome()))
         {
            return null;
         }
      }
      return createParte(parte.getNome(), tipoParte);
   }

   private ParteDTO createParte(String nome, TipoParte tipoParte)
   {
      ParteDTO parteNova = new ParteDTO();
      parteNova.setNome(nome);
      parteNova.setTipo(tipoParte);
      return parteNova;
   }

   public List<ProcessoDto> buscaProcessosPorNumeroDoProcesso(String perfil, String numeroProcesso, String codComarca)
            throws Exception
   {
//      WSCpuFacade cpuFacade = autenticarCPU();
//      try
//      {
//         return cpuFacade.buscaProcessosPorNumeroDoProcesso(perfil, numeroProcesso, codComarca);
//      }
//      catch (Exception e)
//      {
//         e.printStackTrace();
//         throw e;
//      }
	   return null;
   }

   /**
    * Retorna o valor da ação do processo
    * */
   public List<ProcessoDto> listaOutrasInformacoes(String perfil, String numeroProcesso, String codComarca)
            throws CronosException
   {
//      WSCpuFacade cpuFacade = autenticarCPU();
//      try
//      {
//         return cpuFacade.listaOutrasInformacoes(perfil, numeroProcesso, codComarca);
//      }
//      catch (Exception e)
//      {
//         e.printStackTrace();
//      }
      return null;
   }

//   private WSCpuFacade autenticarCPU()
//   {
//      CpuClientWebServiceFactory cpuFactory = CpuClientWebServiceFactory.getInstance();
//      WSCpuFacade cpuFacade = cpuFactory.getPort();
//      cpuFactory.setAuthentication(cpuFacade, ConstantUtil.USER_CPU, ConstantUtil.PSWD_CPU);
//      return cpuFacade;
//   }
}
