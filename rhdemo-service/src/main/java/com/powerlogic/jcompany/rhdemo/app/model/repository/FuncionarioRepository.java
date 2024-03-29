package com.powerlogic.jcompany.rhdemo.app.model.repository;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.qbe.Range;
import com.powerlogic.jcompany.core.model.qbe.SearchParameters;
import com.powerlogic.jcompany.core.model.repository.PlcAbstractRepository;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FotoEntity;
import com.powerlogic.jcompany.rhdemo.app.model.entity.funcionario.FuncionarioEntity;

import javax.persistence.metamodel.ManagedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncionarioRepository extends PlcAbstractRepository<Long, FuncionarioEntity> {
	
	public FuncionarioEntity save(FuncionarioEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	public Class<FuncionarioEntity> getEntityType() {
		return FuncionarioEntity.class;
	}

	@Override
	public List<FuncionarioEntity> findAll(FuncionarioEntity funcionario) {
	
		try {
			
			funcionario.setTemCursoSuperior(null);
			
			SearchParameters sp = new SearchParameters();
			Range<FuncionarioEntity, Date> dataNascimentoRange = null;

			if (funcionario.getDataNascimentoFiltroInicio() != null || funcionario.getDataNascimentoFiltroFim() != null){
				
				ManagedType<FuncionarioEntity> mt = getEntityManager().getMetamodel().entity(FuncionarioEntity.class);
				
				if(funcionario.getDataNascimentoFiltroInicio() != null) {
					dataNascimentoRange = Range.newRange(mt.getAttribute("dataNascimento"));
					dataNascimentoRange.from(funcionario.getDataNascimentoFiltroInicio());
				}

				if(funcionario.getDataNascimentoFiltroFim() != null) {
					if(dataNascimentoRange == null) {
						dataNascimentoRange = Range.newRange(mt.getAttribute("dataNascimento"));
					}
					dataNascimentoRange.to(funcionario.getDataNascimentoFiltroFim() );
				}

				if(dataNascimentoRange != null) {
					sp.range(dataNascimentoRange);
				}

			}
			
			
			return find(funcionario, sp);
			
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
		
	
	}
	
	/** Recupera a lista de funcionarios.
	 * 
	 * @return
	 */
	public List<FuncionarioEntity> recuperaListaFuncionariosAtivos(){
		
		SearchParameters sp = new SearchParameters();
		sp.setNamedQuery("FuncionarioEntity.find");
		Map<String, Object> parameters =  new HashMap<>();
		parameters.put("situacao", PlcSituacao.A);
		sp.setNamedQueryParameters(parameters);
		return find(sp);
		
	}
	
	
	

	public FotoEntity getFoto(Long idFoto) {
		FotoEntity fe= getEntityManager().find(FotoEntity.class, idFoto);
		fe.getConteudo(); // inicializando o conteudo
		return fe;
	}

}