package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.TipoLabel;

public class LabelDTO
{

   TipoLabel tipo;
   String label;

   /**
    * @return the tipo
    */
   public TipoLabel getTipo()
   {
      return tipo;
   }

   /**
    * @param tipo the tipo to set
    */
   public void setTipo(TipoLabel tipo)
   {
      this.tipo = tipo;
   }

   /**
    * @return the label
    */
   public String getLabel()
   {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label)
   {
      this.label = label;
   }
}
