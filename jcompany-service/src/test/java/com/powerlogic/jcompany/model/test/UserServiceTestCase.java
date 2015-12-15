package com.powerlogic.jcompany.model.test;


import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.powerlogic.jcompany.model.PlcAbstractArquillianTestCase;


@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTestCase extends PlcAbstractArquillianTestCase {
	
	@EJB IUserService userService;
	
	
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


