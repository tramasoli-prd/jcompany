/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.fileupload;

import com.powerlogic.jcompany.commons.util.fileupload.PlcFileUploadUtil;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


/** Servlet responsavel pelo upload de arquivos.
 * 
 * @author leandro.silva
 *
 */
@WebServlet("/uploadFiles")
@MultipartConfig(
		fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
		maxFileSize         = 1024 * 1024 * 10, // 10 MB
		maxRequestSize      = 1024 * 1024 * 15 // 15 MB
		)
public class PlcFileUploadServlet extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PlcFileUploadUtil fileUploadUtil;



	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)  {

		try{
			
			StringBuilder retorno = new StringBuilder("{");

			//Get all the parts from request and write it to the file on server
			for (Part part : request.getParts()) {
				InputStream inputStrem = part.getInputStream();

				try {

					String subDiretorio = request.getContextPath().substring(request.getContextPath().indexOf("/")+1);
					if (request.getUserPrincipal()!=null) {
						subDiretorio = subDiretorio.concat(File.separator).concat(request.getUserPrincipal().getName()); 
					}
					fileUploadUtil.saveFile(subDiretorio, getFileName(part), inputStrem);

				} catch (SecurityException | IllegalArgumentException e) {
					e.printStackTrace();
				}

				retorno.append("\"file\" : ").append(StringUtils.substringAfter(part.getHeader("content-disposition"), "filename="));

			}
			retorno.append("}");
			response.setContentType("application/json");
			// Get the printwriter object from response to write the required json object to the output stream      
			PrintWriter out = response.getWriter();
			// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
			out.print(retorno.toString());

			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Headers", "GET, POST, OPTIONS");
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			response.setHeader("Access-Control-Max-Age", "true");

		} catch(Exception e ) {
			try {
				response.setStatus(Status.NOT_ACCEPTABLE.getStatusCode());
				StringBuilder retorno = new StringBuilder("");
				retorno.append("{\"messages\":{\"ERROR\":[{\"key\":\"FALHA_ADICIONAR_ARQUIVO_UPLOAD_026\",\"type\":\"ERROR\",\"message\":\"FALHA_ADICIONAR_ARQUIVO_UPLOAD_026\",\"args\":[\""+e.getMessage()+"\"]}]}}");
				response.setContentType("application/json");	
				PrintWriter out = response.getWriter();
				out.print(retorno.toString());	
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Allow-Headers", "GET, POST, OPTIONS");
				response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
				response.setHeader("Access-Control-Max-Age", "true");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/** recupera o filename
	 *
	 * @param part
	 * @return
     */
	private String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}


	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doOptions(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Max-Age", "true");


		super.doOptions(request, response);
	}
}