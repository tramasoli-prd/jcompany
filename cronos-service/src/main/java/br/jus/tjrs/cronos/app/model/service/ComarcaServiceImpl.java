package br.jus.tjrs.cronos.app.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.app.util.HttpUtil;
import br.jus.tjrs.cronos.core.CronosException;

public class ComarcaServiceImpl implements ComarcaService
{

   private Map<String, String> MAPA_COMARMA = new HashMap<String, String>();
   private final String PATTERN_COMARCAS = "<COMARCAS>(.*?)</COMARCAS>";

   private final String PATTERN_COMARCA = "<COMARCA>(.*?)</COMARCA>";
   private final String COD_COMPONENTE = "COD_COMPONENTE";
   private final String NOME = "NOME";

   private final String URL_SERVICO = "http://xml.tjrs.gov.br/xml/pesquisa/?dir=diversos&script=comarcas&conexao=corporativo";

   @Override
   public String buscaComarca(Integer idComarca) throws CronosException
   {
      if (idComarca == null || idComarca == 0)
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
      return this.buscaComarca(idComarca.toString());
   }

   @Override
   public String buscaComarca(String idComarca) throws CronosException
   {
      if (idComarca == null || idComarca.isEmpty())
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
      if (MAPA_COMARMA.isEmpty())
      {
         carregarComarca();
      }
      return MAPA_COMARMA.get(idComarca.toString());
   }

   private void carregarComarca() throws CronosException
   {
      String retorno;
      try
      {
         retorno = HttpUtil.sendGet(URL_SERVICO);
      }
      catch (Exception e)
      {
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
      Pattern regex = Pattern.compile(PATTERN_COMARCAS, Pattern.DOTALL);
      Matcher matcher = regex.matcher(retorno);
      if (matcher.find())
      {
         Pattern regexCom = Pattern.compile(PATTERN_COMARCA, Pattern.DOTALL);
         String comarcas = matcher.group(1);
         Matcher matcher2 = regexCom.matcher(comarcas);
         while (matcher2.find())
         {
            String comarca = matcher2.group(1).replace(ConstantUtil.HIGGER, ConstantUtil.EMPTY)
                     .replace(ConstantUtil.SMALLER, ConstantUtil.EMPTY)
                     .replace(ConstantUtil.SLASH, ConstantUtil.EMPTY);
            String codigo = StringUtils.substringBetween(comarca, COD_COMPONENTE);
            String nome = StringUtils.substringBetween(comarca, NOME);
            if (!codigo.isEmpty())
            {
               MAPA_COMARMA.put(codigo, nome);
            }
         }
      }
   }
}
