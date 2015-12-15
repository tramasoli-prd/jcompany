package com.powerlogic.jcompany.rhdemo.app.model.service;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.powerlogic.jcompany.model.test.PlcAbstractArquillianTestCase;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.DepartamentoRepository;


public abstract class AppAbstractArquillianTestCase extends PlcAbstractArquillianTestCase {
	

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive j = ShrinkWrap.create(JavaArchive.class);
		complementTestArchive(j);

		j.addPackages(true, DepartamentoEntity.class.getPackage());
		j.addPackages(true, DepartamentoRepository.class.getPackage());
		j.addPackages(true, IDepartamentoService.class.getPackage());

		j.addAsResource("META-INF/persistence.xml", ArchivePaths.create("META-INF/persistence.xml"));
		j.addAsResource("META-INF/beans.xml", ArchivePaths.create("META-INF/beans.xml"));
		j.addAsResource("PlcMessages.properties", ArchivePaths.create("PlcMessages.properties"));
		j.addAsResource("AppMessages.properties", ArchivePaths.create("AppMessages.properties"));

		return j;
	}
		
}


