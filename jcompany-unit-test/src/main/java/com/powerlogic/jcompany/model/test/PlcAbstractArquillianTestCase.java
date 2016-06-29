package com.powerlogic.jcompany.model.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.commons.util.fileupload.PlcFileUploadUtil;
import com.powerlogic.jcompany.commons.util.interpolator.IResourceBundleLocator;
import com.powerlogic.jcompany.commons.util.message.PlcMsgUtil;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationInvariantUtil;
import com.powerlogic.jcompany.commons.validation.CepValidator;
import com.powerlogic.jcompany.core.PlcApplicationLifecycle;
import com.powerlogic.jcompany.core.messages.PlcMessageMap;
import com.powerlogic.jcompany.core.rest.messages.PlcResponseEntity;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Classe abstrata de testes com arquillian
 * 
 * @author george
 * 
 */
public abstract class PlcAbstractArquillianTestCase {


	/**
	 * Complementa o archive com informações adicionais
	 * 
	 * @param j
	 */
	protected static void complementTestArchive(WebArchive j) {

        j.addPackages(true, CepValidator.class.getPackage());
        j.addPackages(true, PlcValidationInterceptor.class.getPackage());
        j.addPackages(true, PlcFileUploadUtil.class.getPackage());
        j.addPackages(true, IResourceBundleLocator.class.getPackage());
        j.addPackages(true, PlcMsgUtil.class.getPackage());
        j.addPackages(true, PlcValidationInvariantUtil.class.getPackage());
        j.addPackages(true, PlcApplicationLifecycle.class.getPackage());
 
	}


	protected PlcResponseEntity resolveResposta(Response response, TypeReference<?> typeReference) {

		try {

			InputStream input = response.readEntity(InputStream.class);
			String json = IOUtils.toString(input);
			JSONObject jsonObject = new JSONObject(json);
			ObjectMapper mapper = new ObjectMapper();
			PlcResponseEntity plcResponse = new PlcResponseEntity();
			plcResponse.setEntity(mapper.readValue(jsonObject.getString("entity"), typeReference));
			plcResponse.setMessageMap(mapper.readValue(jsonObject.getString("messageMap"), PlcMessageMap.class));

			return plcResponse;

		} catch (Exception e) {
			return null;
		} 

	}

	

}
