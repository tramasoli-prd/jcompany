package com.powerlogic.jcompany.rhdemo.app.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


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
public class FileUploadServlet extends HttpServlet {

	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		StringBuilder retorno = new StringBuilder("{");

		//Get all the parts from request and write it to the file on server
		for (Part part : request.getParts()) {
			InputStream inputStrem = part.getInputStream();
			
			try {
				Field fileName = part.getClass().getDeclaredField("fileName");
				fileName.setAccessible(true);

				// write the inputStream to a FileOutputStream
				FileOutputStream outputStream = 
						new FileOutputStream(System.getProperty("java.io.tmpdir").concat(File.separator).concat((String) fileName.get(part)));

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStrem.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				outputStream.flush();
				outputStream.close();
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
			retorno.append("\"file\" : \"").append(part.getName()).append("\"");

		}
		retorno.append("}");
		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream      
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(retorno.toString());
		
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Max-Age", "true");
		response.setHeader("Access-Control-Allow-Credentials", "0");
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
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Max-Age", "true");
		response.setHeader("Access-Control-Allow-Credentials", "0");

		super.doOptions(request, response);
	}
}