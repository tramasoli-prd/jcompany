package br.jus.tjrs.cronos.app.model.dto;

import java.util.ArrayList;
import java.util.List;

public class GrupoElementoDocumentoDTO
{
   private int ordem;
   private List<ElementoDocumentoDTO> elemento = new ArrayList<ElementoDocumentoDTO>();
   private String tipoElemento;

   /**
    * @return the ordem
    */
   public int getOrdem()
   {
      return ordem;
   }

   /**
    * @param ordem the ordem to set
    */
   public void setOrdem(int ordem)
   {
      this.ordem = ordem;
   }

   /**
    * @return the elemento
    */
   public List<ElementoDocumentoDTO> getElemento()
   {
      return elemento;
   }

   /**
    * @param elemento the elemento to set
    */
   public void setElemento(List<ElementoDocumentoDTO> elemento)
   {
      this.elemento = elemento;
   }

   /**
    * @return the tipoElemento
    */
   public String getTipoElemento()
   {
      return tipoElemento;
   }

   /**
    * @param tipoElemento the tipoElemento to set
    */
   public void setTipoElemento(String tipoElemento)
   {
      this.tipoElemento = tipoElemento;
   }

}
