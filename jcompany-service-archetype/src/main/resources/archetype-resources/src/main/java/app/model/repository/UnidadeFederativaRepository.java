#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.model.repository;

import javax.enterprise.context.ApplicationScoped;

import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.model.repository.PlcAbstractRepository;
import ${package}.app.model.entity.UnidadeFederativaEntity;

@ApplicationScoped
public class UnidadeFederativaRepository extends PlcAbstractRepository<Long, UnidadeFederativaEntity> {
	
	public UnidadeFederativaEntity save(UnidadeFederativaEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	public Class<UnidadeFederativaEntity> getEntityType() {
		return UnidadeFederativaEntity.class;
	}

	public UnidadeFederativaEntity findByNome(String nome) {
		return null;
	}

}