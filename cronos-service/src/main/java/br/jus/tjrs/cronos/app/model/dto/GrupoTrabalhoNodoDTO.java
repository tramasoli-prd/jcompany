package br.jus.tjrs.cronos.app.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GrupoTrabalhoNodoDTO extends AbstractNodeDTO
{
   public GrupoTrabalhoNodoDTO()
   {
   }

   public GrupoTrabalhoNodoDTO(Long id)
   {
      setId(id);
   }
}
