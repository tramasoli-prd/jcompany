#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.model.service;

import javax.ejb.Local;

import com.powerlogic.jcompany.core.PlcException;
import com.powerlogic.jcompany.core.model.service.PlcEntityService;
import ${package}.app.model.entity.UnidadeFederativaEntity;

@Local
public interface UnidadeFederativaService extends PlcEntityService <Long, UnidadeFederativaEntity> {
   
	UnidadeFederativaEntity findByNome(String nome) throws PlcException;


}
