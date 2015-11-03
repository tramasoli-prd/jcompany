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

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FileUtils;

public class PlcFileUploadUtil {

	public void saveFile(String filename, InputStream inputStream) {
		
		try{
			// write the inputStream to a FileOutputStream
			FileOutputStream outputStream = 
					new FileOutputStream(System.getProperty("java.io.tmpdir").concat(File.separator).concat(filename));

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
	
	public PlcFileDTO getFile(String filename) {
		
		try{
			PlcFileDTO fileDTO = new PlcFileDTO();
			
			File file = new File(System.getProperty("java.io.tmpdir").concat(File.separator).concat(filename));
			byte[] binaryContent = FileUtils.readFileToByteArray(file);
			
			fileDTO.setNome(filename);
			fileDTO.setTamanho(binaryContent.length);
			fileDTO.setTipo(new MimetypesFileTypeMap().getContentType(file));
			fileDTO.setBinaryContent(binaryContent);
			
			return fileDTO;
			
		} catch (SecurityException | IllegalArgumentException | IOException e) {
			e.printStackTrace();
			return null;
		} 
		

	}



}
