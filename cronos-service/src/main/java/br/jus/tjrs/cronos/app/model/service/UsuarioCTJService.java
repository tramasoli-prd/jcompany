package br.jus.tjrs.cronos.app.model.service;

import java.util.List;

import br.jus.tjrs.cronos.app.model.dto.UsuarioCTJDTO;
import br.jus.tjrs.cronos.core.CronosException;

public interface UsuarioCTJService
{
   List<UsuarioCTJDTO> listaPessoas(String parteNome) throws CronosException;
}
