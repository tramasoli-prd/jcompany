package com.powerlogic.jcompany.model.test;


import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
	public static JavaArchive createTestArchive() {
		JavaArchive j = ShrinkWrap.create(JavaArchive.class);
		complementTestArchive(j);

		j.addPackages(true, UserEntity.class.getPackage());

		j.addAsResource("META-INF/persistence.xml", ArchivePaths.create("META-INF/persistence.xml"));
		j.addAsResource("META-INF/beans.xml", ArchivePaths.create("META-INF/beans.xml"));
		j.addAsResource("PlcMessages.properties", ArchivePaths.create("PlcMessages.properties"));
		j.addAsResource("AppMessages.properties", ArchivePaths.create("AppMessages.properties"));

		return j;
	}

	@Test
	public void testAInsereFuncionario() throws Exception
	{

		UserEntity usuario = new UserEntity();
		usuario.setName("Baldini");

		usuario = (UserEntity)userService.save(usuario);

		Assert.assertTrue(usuario.getId()==1);

	}

	@Test
	public void testBVerificaFuncionario() {

		UserEntity usuario = (UserEntity)userService.get(1L);

		Assert.assertTrue(usuario!=null);
		Assert.assertTrue(usuario.getId()==1);
		Assert.assertTrue(usuario.getName().equals("Baldini"));

	}	

	@Test
	public void testCExcluirFuncionarioComArquivo() throws Exception
	{

		UserEntity usuario = new UserEntity();
		usuario.setId(1l);
		usuario.setVersao(1);

		userService.remove(usuario);

		Assert.assertTrue(true);

	}	

	@Test
	public void testDVerificaFuncionarioComArquivo() throws Exception
	{

		try {
			UserEntity usuario = (UserEntity)userService.get(1L);
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(e.toString().contains("Não foi possível recuperar um registro"));
		}


	}		
}


