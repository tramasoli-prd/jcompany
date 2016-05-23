package com.powerlogic.jcompany.rhdemo.app.rest.entity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.messages.PlcMessageType;
import org.apache.commons.lang3.StringUtils;

import com.powerlogic.jcompany.commons.util.fileupload.PlcFileDTO;
import com.powerlogic.jcompany.commons.util.fileupload.PlcFileUploadUtil;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticated;
import com.powerlogic.jcompany.core.rest.entity.PlcAbstractEntityRest;
import com.powerlogic.jcompany.core.rest.messages.PlcMessageIntercept;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoConteudoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FuncionarioEntity;
import com.powerlogic.jcompany.rhdemo.app.model.service.FuncionarioService;

@PlcAuthenticated
@Path("/entity/funcionario")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
@PlcMessageIntercept
public class FuncionarioRest extends PlcAbstractEntityRest<Long, FuncionarioEntity, Object> {

	@Inject
	private FuncionarioService funcionarioService;

	@Inject
	private PlcFileUploadUtil fileUploadUtil;


	@Override
	protected FuncionarioService getEntityService() {
		return funcionarioService;
	}

	@GET
	@Path("/foto/{id}")
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public Response getFoto(@PathParam("id") Long idFoto) {

		FotoEntity fotoEntity = funcionarioService.getFoto(idFoto);

		ResponseBuilder response = Response.ok(fotoEntity.getConteudo().getBinaryContent());

		response.header("Content-Disposition", "inline; filename=\""+fotoEntity.getNome()+"\"; size=\""+fotoEntity.getTamanho()+"\"");
		response.header("Content-Type", fotoEntity.getTipo());
		response.header("Content-Length", fotoEntity.getTamanho());

		return response.build();

	}

	@POST
	@Path("/savefunc")
	public FuncionarioEntity save(@Context HttpServletRequest request, @Context UriInfo ui, FuncionarioEntity entity) throws PlcException {
		recuperaFotoDoDisco(request, entity);
		return super.save(entity);
	}



	private void recuperaFotoDoDisco(HttpServletRequest request, FuncionarioEntity entity) {
		if (StringUtils.isNotEmpty(entity.getUploadFileName())) {

			String subDiretorio = request.getContextPath();
			if (request.getUserPrincipal()!=null) {
				subDiretorio = subDiretorio.concat(File.separator).concat(request.getUserPrincipal().getName()); 
			}			
			PlcFileDTO fileDTO = fileUploadUtil.getFile(subDiretorio, entity.getUploadFileName());
			if (fileDTO!=null) {
				entity.setFoto(new FotoEntity());
				entity.getFoto().setNome(fileDTO.getNome());
				entity.getFoto().setTamanho(fileDTO.getTamanho());
				entity.getFoto().setTipo(fileDTO.getTipo());
				entity.getFoto().setConteudo(new FotoConteudoEntity());
				entity.getFoto().getConteudo().setBinaryContent(fileDTO.getBinaryContent());
				entity.getFoto().getConteudo().setFoto(entity.getFoto());
			}
		}
	}


}
