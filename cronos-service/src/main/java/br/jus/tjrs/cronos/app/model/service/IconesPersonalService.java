package br.jus.tjrs.cronos.app.model.service;

import br.jus.tjrs.cronos.app.model.dto.CssDTO;
import br.jus.tjrs.cronos.app.model.entity.IconesPersonalEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

public interface IconesPersonalService extends EntityService<Long, IconesPersonalEntity>
{

   CssDTO css() throws CronosException;
}
