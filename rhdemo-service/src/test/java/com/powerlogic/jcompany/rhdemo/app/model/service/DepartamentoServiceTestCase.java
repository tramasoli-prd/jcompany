package com.powerlogic.jcompany.rhdemo.app.model.service;


import java.util.List;

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
	public void insereDepartamento() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setDescricao("Departamento Test 1");
		departamento = (DepartamentoEntity)departamentoService.save(departamento);		
		Assert.assertTrue(departamento.getId()==1);
		
	}
	
	@Test
	public void insereDepartamento2() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setDescricao("Departamento Test 2");
		departamento.setDepartamentoPai(new DepartamentoEntity(1L));
		departamento = (DepartamentoEntity)departamentoService.save(departamento);		
		Assert.assertTrue(departamento.getId()==2);
		
	}
	
	@Test
	public void pesquisaDepartamentoPaiNull() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setPesquisaPaiIsNull(true);
		List<DepartamentoEntity> departamentos = departamentoService.findAll(departamento);
		Assert.assertTrue(departamentos.size()==1);
		Assert.assertTrue(departamentos.get(0).getId()==1);
		
	}
	
	@Test
	public void pesquisaDepartamentoPaiNotNull() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setPesquisaPaiIsNotNull(true);
		List<DepartamentoEntity> departamentos = departamentoService.findAll(departamento);
		Assert.assertTrue(departamentos.size()==1);
		Assert.assertTrue(departamentos.get(0).getId()==2);
		
	}

	@Test
	public void pesquisaDepartamentoDescricaoNull() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setPesquisaDescricaoIsNull(true);
		List<DepartamentoEntity> departamentos = departamentoService.findAll(departamento);
		Assert.assertTrue(departamentos.size()==0);
		
	}

	@Test
	public void pesquisaDepartamentoDescricaoNotNull() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setPesquisaDescricaoIsNotNull(true);
		List<DepartamentoEntity> departamentos = departamentoService.findAll(departamento);
		Assert.assertTrue(departamentos.size()==2);
		Assert.assertTrue(departamentos.get(0).getId()==1);
		
	}
}


