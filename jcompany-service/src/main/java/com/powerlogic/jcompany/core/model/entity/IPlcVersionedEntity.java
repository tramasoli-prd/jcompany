/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Interface para utilizar a Auditoria Mínima
 * 
 * @author Powerlogic
 * @category Entity
 * @since 1.0.0
 */
public interface IPlcVersionedEntity<PK extends Serializable> extends IPlcEntityModel<PK> {

	Integer getVersao();
	void setVersao(Integer versao) ;
	
	public Date getDataCriacao();
	public void setDataCriacao(Date dataCriacao);

	public String getUsuarioAtualizacao();
	public void setUsuarioAtualizacao(String usuarioAtualizacao) ;
	
	public Date getDataAtualizacao();
	public void setDataAtualizacao(Date dataAtualizacao);

}