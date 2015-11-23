package com.powerlogic.jcompany.rhdemo.app.rest.entity;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;

import com.powerlogic.jcompany.commons.util.fileupload.PlcFileDTO;
import com.powerlogic.jcompany.commons.util.fileupload.PlcFileUploadUtil;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.rest.auth.PlcAuthenticated;
import com.powerlogic.jcompany.core.rest.entity.PlcAbstractEntityRest;
import com.powerlogic.jcompany.core.rest.messages.PlcMessageIntercept;
import com.powerlogic.jcompany.core.rest.util.PlcDateParam;
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

	@GET
	@Path("/all")
	public List<FuncionarioEntity> findAll(@Context HttpServletRequest request, @Context UriInfo ui,
			 @QueryParam("nome") String nome,
			 @QueryParam("cpf") String cpf,
			 @QueryParam("email") String email,
			 @QueryParam("dataNascimento") PlcDateParam dataNascimento) throws PlcException {

		FuncionarioEntity funcionario = new FuncionarioEntity();

		if (StringUtils.isNoneBlank(nome)) {
			funcionario.setNome(nome);
		}

		if (StringUtils.isNoneBlank(cpf)) {
			funcionario.setCpf(cpf);
		}

		if (dataNascimento != null) {
			funcionario.setDataNascimento(dataNascimento.getDate());
		}
		
		if (StringUtils.isNoneBlank(email)) {
			funcionario.setEmail(email);
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
		setMasterIntoDetails(entity, entity.getDependente(), "funcionario");
		setMasterIntoDetails(entity, entity.getHistoricoProfissional(), "funcionario");
		recuperaFotoDoDisco(request, entity);
		return super.save(entity);
	}



	private void recuperaFotoDoDisco(HttpServletRequest request, FuncionarioEntity entity) {
		if (StringUtils.isNotEmpty(entity.getFotoFileName())) {

			String subDiretorio = request.getContextPath();
			if (request.getUserPrincipal()!=null) {
				subDiretorio = subDiretorio.concat(File.separator).concat(request.getUserPrincipal().getName()); 
			}			
			PlcFileDTO fileDTO = fileUploadUtil.getFile(subDiretorio, entity.getFotoFileName());
			if (fileDTO!=null) {
				//if (entity.getFoto() == null){
					entity.setFoto(new FotoEntity());
				//}
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
