package br.jus.tjrs.cronos.app.rest.cpu;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.jus.tjrs.cronos.app.model.dto.CarregarProcessoDTO;
import br.jus.tjrs.cronos.app.model.dto.ProcessoDTO;
import br.jus.tjrs.cronos.app.model.service.CPUService;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.rest.AbstractRest;
import br.jus.tjrs.cronos.core.rest.auth.Authenticated;
import br.jus.tjrs.ws.client.wscpu.ProcessoDto;

@Path("/cpu")
@Authenticated
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class CPURest extends AbstractRest
{

   @Inject
   private CPUService cpuService;

   @POST
   @Path("/listaProcessos")
   public List<ProcessoDto> listaProcessos(CarregarProcessoDTO listaProcessos) throws Exception
   {
      return cpuService.listaProcessos(listaProcessos.getPerfil(), listaProcessos.getNumeroProcesso(),
               listaProcessos.getComarca());
   }

   @POST
   @Path("/buscaProcessosPorNumeroDoProcesso")
   public List<ProcessoDto> buscaProcessosPorNumeroDoProcesso(CarregarProcessoDTO listaProcessos) throws Exception
   {
      return cpuService.buscaProcessosPorNumeroDoProcesso(listaProcessos.getPerfil(),
               listaProcessos.getNumeroProcesso(), listaProcessos.getComarca());
   }

   @POST
   @Path("/carregarProcesso")
   public ProcessoDTO carregaProcesso(CarregarProcessoDTO carregarProcesso) throws CronosException
   {
      return cpuService.carregarProcesso(carregarProcesso.getNumeroProcesso());
   }
}
