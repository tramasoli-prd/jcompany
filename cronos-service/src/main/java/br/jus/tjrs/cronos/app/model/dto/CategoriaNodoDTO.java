package br.jus.tjrs.cronos.app.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.app.model.domain.SimNao;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaNodoDTO extends AbstractNodeDTO
{

   private SimNao compartilhar;

   public SimNao getCompartilhar()
   {
      return compartilhar;
   }

   public void setCompartilhar(SimNao compartilhar)
   {
      this.compartilhar = compartilhar;
   }

}
