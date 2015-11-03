package br.jus.tjrs.cronos.core.model.entity;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.jus.tjrs.cronos.app.model.domain.Situacao;

public class VersionedListener
{
   private String usuario = "CRONOS";

   @PrePersist
   public void prePersist(VersionedEntity versionedEntity)
   {
      if (versionedEntity.getSituacao() == null)
      {
         versionedEntity.setSituacao(Situacao.A);
      }
      versionedEntity.setDataCriacao(new Date());
      update(versionedEntity, versionedEntity.getDataCriacao());
      preUpdate(versionedEntity);
   }

   @PreUpdate
   public void preUpdate(VersionedEntity versionedEntity)
   {
      update(versionedEntity, new Date());
   }

   private void update(VersionedEntity versionedEntity, Date data)
   {
      versionedEntity.setDataAtualizacao(data);
      if (versionedEntity.getUsuarioAtualizacao() == null)
      {
         versionedEntity.setUsuarioAtualizacao(usuario);
      }
   }
}
