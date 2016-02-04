package com.powerlogic.jcompany.rhdemo.app.model.service;


import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.type.TypeReference;
import com.powerlogic.jcompany.core.rest.messages.PlcResponseEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;

@RunWith(Arquillian.class)
public class DepartamentoRestTestCase  extends AppAbstractArquillianTestCase {

	@EJB IDepartamentoService departamentoService;

	@Test
	@InSequence(1)
	public void insereDepartamento() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setDescricao("Departamento Test 1");
		departamento = (DepartamentoEntity)departamentoService.save(departamento);		
		Assert.assertTrue(departamento.getId()==1);

	}

	@Test
	@InSequence(2)
	public void insereDepartamento2() throws Exception
	{

		DepartamentoEntity departamento = new DepartamentoEntity();
		departamento.setDescricao("Departamento Test 2");
		departamento.setDepartamentoPai(new DepartamentoEntity(1L));
		departamento = (DepartamentoEntity)departamentoService.save(departamento);		
		Assert.assertTrue(departamento.getId()==2);

	}

	@Test
	@RunAsClient
	@InSequence(3)
	public void editarDepartamento(@ArquillianResteasyResource WebTarget webTarget) {

		Response r = webTarget.path("/auth/login").request()
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("Accept", MediaType.APPLICATION_JSON)
				.post(Entity.json("{\"username\":\"admin\",\"password\":\"senha\"}"));


		Response r2 = webTarget.path("/entity/departamento/get/1").request()
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("Accept", MediaType.APPLICATION_JSON)
				.cookie(r.getCookies().get("JSESSIONID"))
				.get();

		Assert.assertTrue("Status:"+r2.getStatus(), r2.getStatus()==200);
		
		PlcResponseEntity plcResponse = resolveResposta(r2, new TypeReference<DepartamentoEntity>() {});
		
		Assert.assertTrue(((DepartamentoEntity)plcResponse.getEntity()).getId()==1);
		
	}

	@Test
	@RunAsClient
	@InSequence(4)
	public void pesquisaDepartamento(@ArquillianResteasyResource WebTarget webTarget) {

		Response r = webTarget.path("/auth/login").request()
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("Accept", MediaType.APPLICATION_JSON)
				.post(Entity.json("{\"username\":\"admin\",\"password\":\"senha\"}"));
		
		Response r2 = webTarget.path("/entity/departamento/findAll").request()
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.header("Accept", MediaType.APPLICATION_JSON)
				.cookie(r.getCookies().get("JSESSIONID"))
				.get();

		Assert.assertTrue("Status:"+r2.getStatus(), r2.getStatus()==200);
		
		PlcResponseEntity plcResponse = resolveResposta(r2, new TypeReference<ArrayList<DepartamentoEntity>>() {});

		Assert.assertTrue(((ArrayList<DepartamentoEntity>)plcResponse.getEntity()).size()==2);
		
	}

}


