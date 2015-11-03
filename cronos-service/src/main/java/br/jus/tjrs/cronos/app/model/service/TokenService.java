package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Local;

import br.jus.tjrs.cronos.app.model.entity.TokenEntity;
import br.jus.tjrs.cronos.core.model.service.EntityService;

@Local
public interface TokenService extends EntityService<Long, TokenEntity>
{

}
