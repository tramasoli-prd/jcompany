package br.jus.tjrs.cronos.app.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.jus.tjrs.cronos.app.model.service.SentencaService;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;

@WebServlet("/exportarSentenca")
public class SentencaServlet extends javax.servlet.http.HttpServlet
{

   private static final String PARAM_OBRIGATORIO = "Número do processo é obrigatório.";
   private static final String PARAM_NUMERO_PROCESSO = "numeroProcesso";
   private static final String PARAM_ID = "id";
   private static final String PARAM_EXTENSAO = "extensao";
   private static final String ATTACHMENT = "attachment; filename=\"%s\"";

   @Inject
   public SentencaService sentencaService;
   private static final long serialVersionUID = 1L;

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
   {
      final String numeroProcesso = request.getParameter(PARAM_NUMERO_PROCESSO);
      final String id = request.getParameter(PARAM_ID);
      final String extensao = request.getParameter(PARAM_EXTENSAO);

      if (numeroProcesso == null || numeroProcesso.isEmpty())
      {
         throw new IOException(PARAM_OBRIGATORIO);
      }

      String exportarSentenca = null;
      try
      {
         if (id != null && !id.isEmpty())
         {
            exportarSentenca = sentencaService.exportarSentenca(Long.valueOf(id));
         }
         else
         {
            exportarSentenca = sentencaService.exportarSentenca(numeroProcesso);
         }
      }
      catch (CronosException e)
      {
         e.printStackTrace();
         return;
      }

      response.setContentType(ConstantUtil.APPLICATION_OCTET_STREAM);
      String nomeSentencaGerada = ConstantUtil.NOME_ARQUIVO_SENTENCA + ConstantUtil.DOT
               + (extensao == null ? ConstantUtil.DOC : extensao);
      response.setHeader(ConstantUtil.CONTENT_DISPOSITION,
               String.format(ATTACHMENT, nomeSentencaGerada));
      OutputStream out = response.getOutputStream();

      exportarSentenca = ConstantUtil.HTML_BODY + exportarSentenca + ConstantUtil.BODY_HTML;
      File file;
      if (ConstantUtil.ODT.equals(extensao))
      {
         file = new File(ConstantUtil.TEMPLATE_ODT);
         exportarSentenca = exportarSentenca
                  .replaceAll(ConstantUtil.BLOCKQUOTE_LOWER, ConstantUtil.DIV_LOWER)
                  .replaceAll(ConstantUtil.BLOCKQUOTE_UPPER, ConstantUtil.DIV_LOWER);
      }
      else
      {
         file = new File(ConstantUtil.TEMPLATE_DOC);
      }
      try (PrintStream ps = new PrintStream(file))
      {
         ps.println(exportarSentenca);
      }
      response.setContentLength((int) file.length());
      try (FileInputStream in = new FileInputStream(file))
      {
         byte[] buffer = new byte[4096];
         int length;
         while ((length = in.read(buffer)) > 0)
         {
            out.write(buffer, 0, length);
         }
      }
      out.flush();
   }
}
