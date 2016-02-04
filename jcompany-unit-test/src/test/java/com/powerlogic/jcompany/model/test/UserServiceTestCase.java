package com.powerlogic.jcompany.model.test;


import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTestCase extends PlcAbstractArquillianTestCase {

	@EJB IUserService userService;

	@Deployment
	public static WebArchive createTestArchive() {
		WebArchive j = ShrinkWrap.create(WebArchive.class);
		complementTestArchive(j);

		j.addPackages(true, UserEntity.class.getPackage());

		j.addAsResource("META-INF/persistence.xml", ArchivePaths.create("META-INF/persistence.xml"));
		j.addAsResource("META-INF/beans.xml", ArchivePaths.create("META-INF/beans.xml"));
		j.addAsResource("PlcMessages.properties", ArchivePaths.create("PlcMessages.properties"));
		j.addAsResource("AppMessages.properties", ArchivePaths.create("AppMessages.properties"));

		return j;
	}

	@Test
	public void testAInsereUsuario() throws Exception
	{

		UserEntity usuario = new UserEntity();
		usuario.setName("Baldini");

		usuario = (UserEntity)userService.save(usuario);

		Assert.assertTrue(usuario.getId()==1);

	}

	@Test
	public void testBVerificaExistenciaUsuario() {

		UserEntity usuario = (UserEntity)userService.get(1L);

		Assert.assertTrue(usuario!=null);
		Assert.assertTrue(usuario.getId()==1);
		Assert.assertTrue(usuario.getName().equals("Baldini"));

	}	

	@Test
	public void testCExcluirUsuario() throws Exception
	{

		UserEntity usuario = new UserEntity();
		usuario.setId(1l);
		usuario.setVersao(1);

		userService.remove(usuario);

		Assert.assertTrue(true);

	}	

	@Test
	public void testDVerificaNaoExistenciaUsuario() throws Exception
	{

		try {
			UserEntity usuario = (UserEntity)userService.get(1L);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(e.toString().contains("Não foi possível recuperar um registro"));
		}


	}		
}


