package com.powerlogic.jcompany.rhdemo.app.model.service;


import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;


@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoServiceTestCase extends AppAbstractArquillianTestCase {
	
	@EJB IDepartamentoService departamentoService;
	
	
	@Test
	public void insereFuncionario() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setDescricao("Departamento Teste");

		departamento = (DepartamentoEntity)departamentoService.save(departamento);
		
		Assert.assertTrue(departamento.getId()==1);
		
	}

//	@Test
//	public void testBVerificaFuncionario() {
//
//		UserEntity usuario = (UserEntity)userService.get(1L);
//
//		Assert.assertTrue(usuario!=null);
//		Assert.assertTrue(usuario.getId()==1);
//		Assert.assertTrue(usuario.getName().equals("Baldini"));
//		
//	}	
//	
//	@Test
//	public void testCExcluirFuncionarioComArquivo() throws Exception
//	{
//
//		UserEntity usuario = new UserEntity();
//		usuario.setId(1l);
//		usuario.setVersao(1);
//		
//		userService.remove(usuario);
//
//		Assert.assertTrue(true);
//		
//	}	
//	
//	@Test
//	public void testDVerificaFuncionarioComArquivo() throws Exception
//	{
//
//		try {
//			UserEntity usuario = (UserEntity)userService.get(1L);
//			Assert.assertTrue(true);
//		} catch (Exception e) {
//			Assert.assertTrue(e.toString().contains("Não foi possível recuperar um registro"));
//		}
//
//		
//	}		
}


