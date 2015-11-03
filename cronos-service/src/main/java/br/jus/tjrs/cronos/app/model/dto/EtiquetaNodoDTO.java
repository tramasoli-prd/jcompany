package br.jus.tjrs.cronos.app.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EtiquetaNodoDTO extends AbstractNodeDTO
{

   private Long idTaxonomiaEtiqueta;

   /**
    * @return the idTaxonomiaEtiqueta
    */
   public Long getIdTaxonomiaEtiqueta()
   {
      return idTaxonomiaEtiqueta;
   }

   /**
    * @param idTaxonomiaEtiqueta the idTaxonomiaEtiqueta to set
    */
   public void setIdTaxonomiaEtiqueta(Long idTaxonomiaEtiqueta)
   {
      this.idTaxonomiaEtiqueta = idTaxonomiaEtiqueta;
   }

}
