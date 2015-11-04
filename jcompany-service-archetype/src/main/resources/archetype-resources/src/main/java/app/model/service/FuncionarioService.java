#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.app.model.service;

import javax.ejb.Local;

import com.powerlogic.jcompany.core.model.service.PlcEntityService;
import ${package}.app.model.entity.funcionario.FotoEntity;
import ${package}.app.model.entity.funcionario.FuncionarioEntity;

@Local
public interface FuncionarioService extends PlcEntityService<Long, FuncionarioEntity> {

	FotoEntity getFoto(Long idFoto);
   

}
