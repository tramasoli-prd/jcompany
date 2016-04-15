package com.powerlogic.jcompany.rhdemo.app.model.service;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import com.powerlogic.jcompany.model.test.PlcAbstractArquillianTestCase;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.repository.DepartamentoRepository;
import com.powerlogic.jcompany.rhdemo.app.rest.entity.DepartamentoRest;


public abstract class AppAbstractArquillianTestCase extends PlcAbstractArquillianTestCase {
	

	@Deployment
	public static WebArchive createTestArchive() {
		WebArchive j = ShrinkWrap.create(WebArchive.class);
		complementTestArchive(j);

		j.addPackages(true, DepartamentoEntity.class.getPackage());
		j.addPackages(true, DepartamentoRepository.class.getPackage());
		j.addPackages(true, IDepartamentoService.class.getPackage());
		j.addPackages(true, DepartamentoRest.class.getPackage());

		j.addAsResource("META-INF/persistence.xml", ArchivePaths.create("META-INF/persistence.xml"));
		j.addAsResource("META-INF/beans.xml", ArchivePaths.create("META-INF/beans.xml"));
		j.addAsResource("PlcMessages_pt_BR.properties", ArchivePaths.create("PlcMessages_pt_BR.properties"));
		j.addAsResource("AppMessages_pt_BR.properties", ArchivePaths.create("AppMessages_pt_BR.properties"));
		j.setWebXML("META-INF/web.xml");		

		return j;
	}
		
}


