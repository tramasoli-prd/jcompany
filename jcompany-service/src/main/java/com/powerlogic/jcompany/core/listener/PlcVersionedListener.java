/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcVersionedEntity;

public class PlcVersionedListener
{
   private String usuario = "ADMIN";

   @PrePersist
   public void prePersist(IPlcVersionedEntity versionedEntity)
   {
      if (versionedEntity.getSituacao() == null)
      {
         versionedEntity.setSituacao(PlcSituacao.A);
      }
      versionedEntity.setDataCriacao(new Date());
      update(versionedEntity, versionedEntity.getDataCriacao());
      preUpdate(versionedEntity);
   }

   @PreUpdate
   public void preUpdate(IPlcVersionedEntity versionedEntity)
   {
      update(versionedEntity, new Date());
   }

   private void update(IPlcVersionedEntity versionedEntity, Date data)
   {
      versionedEntity.setDataAtualizacao(data);
      if (versionedEntity.getUsuarioAtualizacao() == null)
      {
         versionedEntity.setUsuarioAtualizacao(usuario);
      }
   }
}
