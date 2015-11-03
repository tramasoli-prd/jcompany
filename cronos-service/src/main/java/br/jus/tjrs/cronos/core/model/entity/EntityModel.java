package br.jus.tjrs.cronos.core.model.entity;

import java.io.Serializable;

public interface EntityModel<PK extends Serializable> extends Serializable
{
   PK getId();

   void setId(PK id);
}
