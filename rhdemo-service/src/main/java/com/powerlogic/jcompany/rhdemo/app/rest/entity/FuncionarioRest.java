package com.powerlogic.jcompany.rhdemo.app.rest.entity;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;

import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticated;
import com.powerlogic.jcompany.core.rest.entity.PlcAbstractEntityRest;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FuncionarioEntity;
import com.powerlogic.jcompany.rhdemo.app.model.service.FuncionarioService;

@PlcAuthenticated
@Path("/entity/funcionario")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class FuncionarioRest extends PlcAbstractEntityRest<Long, FuncionarioEntity, Object> {

	@Inject
	private FuncionarioService funcionarioService;

	@GET
	@Path("/all")
	public List<FuncionarioEntity> findAll(@Context HttpServletRequest request, @Context UriInfo ui) throws PlcException {

		MultivaluedMap<String, String> queryParams = ui.getQueryParameters();

		FuncionarioEntity funcionario = new FuncionarioEntity();

		String nome = queryParams.getFirst("nome");

		String cpf = queryParams.getFirst("cpf");

		if (StringUtils.isNoneBlank(nome)) {
			funcionario.setNome(nome);
		}

		if (StringUtils.isNoneBlank(cpf)) {
			funcionario.setCpf(cpf);
		}

		return getEntityService().findAll(funcionario);
	}

	@Override
	protected FuncionarioService getEntityService() {
		return funcionarioService;
	}

	@GET
	@Path("/foto/{id}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response getTextFile(@PathParam("id") Long idFoto) {

		FotoEntity fotoEntity = funcionarioService.getFoto(idFoto);
		
		ResponseBuilder response = Response.ok(fotoEntity.getConteudo().getBinaryContent());

		response.header("Content-Disposition", "inline; filename=\""+fotoEntity.getNome()+"\"; size=\""+fotoEntity.getTamanho()+"\"");
		response.header("Content-Type", fotoEntity.getTipo());
		response.header("Content-Length", fotoEntity.getTamanho());

		return response.build();

	}


}