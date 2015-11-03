package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import br.jus.tjrs.cronos.app.model.dto.IconeDTO;
import br.jus.tjrs.cronos.app.model.entity.EtiquetaGTEntity;
import br.jus.tjrs.cronos.core.CronosException;
import br.jus.tjrs.cronos.core.model.service.EntityService;

public interface EtiquetaGTService extends EntityService<Long, EtiquetaGTEntity>
{
   List<EtiquetaGTEntity> listByPartDescription(String part, Long idGrupoTrabalho) throws CronosException;

   EtiquetaGTEntity findEtiquetaGrupoTrabalho(String etiqueta, Long idGrupoTrabalho) throws CronosException;

   EtiquetaGTEntity alterarIcone(IconeDTO icone) throws CronosException;
}
