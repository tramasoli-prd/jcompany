/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2010-2014.

		Please read licensing information in your installation directory.Contact Powerlogic for more 
		information or contribute with this project: suporte@powerlogic.com.br - www.powerlogic.com.br																								
 */

package com.powerlogic.jcompany.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;

public class PlcFileUploadUtil {

	public void saveFile(String subdiretorio, String filename, InputStream inputStream) {

		try{
			File f = new File(System.getProperty("java.io.tmpdir").concat(File.separator).concat(subdiretorio));
			f.mkdirs();
			
			// write the inputStream to a FileOutputStream
			FileOutputStream outputStream = 
					new FileOutputStream(System.getProperty("java.io.tmpdir").concat(File.separator).concat(subdiretorio).concat(File.separator).concat(filename));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.flush();
			outputStream.close();


		} catch (SecurityException | IllegalArgumentException | IOException e) {
			e.printStackTrace();
		} 

	}

	public PlcFileDTO getFile(String subdiretorio, String filename) {

		try{
			PlcFileDTO fileDTO = new PlcFileDTO();

			File file = new File(System.getProperty("java.io.tmpdir").concat(File.separator).concat(subdiretorio).concat(File.separator).concat(filename));
			byte[] binaryContent = FileUtils.readFileToByteArray(file);

			fileDTO.setNome(filename);
			fileDTO.setTamanho(binaryContent.length);

			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			String type = fileNameMap.getContentTypeFor(System.getProperty("java.io.tmpdir").concat(File.separator).concat(filename));

			fileDTO.setTipo(type);
			fileDTO.setBinaryContent(binaryContent);

			file.deleteOnExit();

			return fileDTO;

		} catch (SecurityException | IllegalArgumentException | IOException e) {
			e.printStackTrace();
			return null;
		} 


	}



}
