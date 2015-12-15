package com.powerlogic.jcompany.model;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;

import com.powerlogic.jcompany.commons.interceptor.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.commons.util.fileupload.PlcFileUploadUtil;
import com.powerlogic.jcompany.commons.util.interpolator.IResourceBundleLocator;
import com.powerlogic.jcompany.commons.util.message.PlcMessageUtil;
import com.powerlogic.jcompany.commons.util.validation.PlcValidationInvariantUtil;
import com.powerlogic.jcompany.commons.validation.CepValidator;
import com.powerlogic.jcompany.core.PlcApplicationLifecycle;
import com.powerlogic.jcompany.model.test.UserEntity;

/**
 * Classe abstrata de testes com arquillian
 * 
 * @author george
 * 
 */
public abstract class PlcAbstractArquillianTestCase {

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive j = ShrinkWrap.create(JavaArchive.class);
		complementTestArchive(j);
		return j;
	}

	/**
	 * Complementa o archive com informações adicionais
	 * 
	 * @param j
	 */
	private static void complementTestArchive(JavaArchive j) {

        j.addPackages(true, CepValidator.class.getPackage());
        j.addPackages(true, PlcValidationInterceptor.class.getPackage());
        j.addPackages(true, PlcFileUploadUtil.class.getPackage());
        j.addPackages(true, IResourceBundleLocator.class.getPackage());
        j.addPackages(true, PlcMessageUtil.class.getPackage());
        j.addPackages(true, PlcValidationInvariantUtil.class.getPackage());
        j.addPackages(true, PlcApplicationLifecycle.class.getPackage());
        j.addPackages(true, UserEntity.class.getPackage());
        
        j.addAsResource("META-INF/persistence.xml", ArchivePaths.create("META-INF/persistence.xml"));
        j.addAsResource("META-INF/beans.xml", ArchivePaths.create("META-INF/beans.xml"));
        j.addAsResource("PlcMessages.properties", ArchivePaths.create("PlcMessages.properties"));
        j.addAsResource("AppMessages.properties", ArchivePaths.create("AppMessages.properties"));
 
	}

	

}
