package com.powerlogic.jcompany.model.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.commons.util.fileupload.PlcFileUploadUtil;
import com.powerlogic.jcompany.commons.util.interpolator.IResourceBundleLocator;
import com.powerlogic.jcompany.commons.util.message.PlcMessageUtil;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationInvariantUtil;
import com.powerlogic.jcompany.commons.validation.CepValidator;
import com.powerlogic.jcompany.core.PlcApplicationLifecycle;

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
	protected static void complementTestArchive(JavaArchive j) {

        j.addPackages(true, CepValidator.class.getPackage());
        j.addPackages(true, PlcValidationInterceptor.class.getPackage());
        j.addPackages(true, PlcFileUploadUtil.class.getPackage());
        j.addPackages(true, IResourceBundleLocator.class.getPackage());
        j.addPackages(true, PlcMessageUtil.class.getPackage());
        j.addPackages(true, PlcValidationInvariantUtil.class.getPackage());
        j.addPackages(true, PlcApplicationLifecycle.class.getPackage());
 
	}

	

}
