#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.validation.Valid;

import com.powerlogic.jcompany.commons.util.PlcFileDTO;
import com.powerlogic.jcompany.commons.util.PlcFileUploadUtil;
import com.powerlogic.jcompany.commons.validation.PlcValidationInterceptor;
import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.model.repository.PlcEntityRepository;
import com.powerlogic.jcompany.core.model.service.PlcAbstractServiceEntity;
import ${package}.app.model.entity.funcionario.FotoConteudoEntity;
import ${package}.app.model.entity.funcionario.FotoEntity;
import ${package}.app.model.entity.funcionario.FuncionarioEntity;
import ${package}.app.model.repository.FuncionarioRepository;

@Stateless
@Interceptors ({PlcValidationInterceptor.class})
public class FuncionarioServiceImpl extends PlcAbstractServiceEntity<Long, FuncionarioEntity> implements FuncionarioService {

	@Inject
	private FuncionarioRepository funcionarioRepository;


	@Override
	protected PlcEntityRepository<Long, FuncionarioEntity> getEntityRepository() {
		return funcionarioRepository;
	}

	@Override
	public FuncionarioEntity save(@Valid FuncionarioEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	public FotoEntity getFoto(Long idFoto) {
		return funcionarioRepository.getFoto(idFoto);
	}
}
