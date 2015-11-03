package br.jus.tjrs.cronos.app.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;
import br.jus.tjrs.cronos.app.model.entity.ElementoIGTEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaCategoriaEntity;
import br.jus.tjrs.cronos.app.model.entity.TaxonomiaEtiquetaGTEntity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaItemDTO
{

   /* Atributo utilizado no response/request */
   private CategoriaItemEntity categoria;

   /* Atributo utilizado no response */
   private List<CategoriaItemEntity> categoriasModelo = new ArrayList<CategoriaItemEntity>();

   /* Atributo utilizado no request */
   private List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiqueta = new ArrayList<TaxonomiaEtiquetaGTEntity>();

   /* Atributo utilizado no request */
   private List<TaxonomiaCategoriaEntity> taxonomiasCategoria = new ArrayList<TaxonomiaCategoriaEntity>();

   /* Atributo utilizado no request */
   private List<TaxonomiaCategoriaEntity> taxonomiasCategoriaNovas = new ArrayList<TaxonomiaCategoriaEntity>();

   /* Atributo utilizado no request */
   private List<ElementoIGTEntity> elementos = new ArrayList<ElementoIGTEntity>();

   /* Atributo utilizado no response */
   private PaiDTO paiGeral;

   /* Atributo utilizado no response */
   private PaiDTO pai;

   public CategoriaItemEntity getCategoria()
   {
      return categoria;
   }

   public void setCategoria(CategoriaItemEntity categoria)
   {
      this.categoria = categoria;
   }

   public List<CategoriaItemEntity> getCategoriasModelo()
   {
      return categoriasModelo;
   }

   public void setCategoriasModelo(List<CategoriaItemEntity> categoriasModelo)
   {
      this.categoriasModelo = categoriasModelo;
   }

   public List<TaxonomiaEtiquetaGTEntity> getTaxonomiasEtiqueta()
   {
      return taxonomiasEtiqueta;
   }

   public void setTaxonomiasEtiqueta(List<TaxonomiaEtiquetaGTEntity> taxonomiasEtiqueta)
   {
      this.taxonomiasEtiqueta = taxonomiasEtiqueta;
   }

   public List<TaxonomiaCategoriaEntity> getTaxonomiasCategoria()
   {
      return taxonomiasCategoria;
   }

   public void setTaxonomiasCategoria(List<TaxonomiaCategoriaEntity> taxonomiasCategoria)
   {
      this.taxonomiasCategoria = taxonomiasCategoria;
   }

   public List<TaxonomiaCategoriaEntity> getTaxonomiasCategoriaNovas()
   {
      return taxonomiasCategoriaNovas;
   }

   public void setTaxonomiasCategoriaNovas(List<TaxonomiaCategoriaEntity> taxonomiasCategoriaNovas)
   {
      this.taxonomiasCategoriaNovas = taxonomiasCategoriaNovas;
   }

   /**
    * @return the elementosNovos
    */
   public List<ElementoIGTEntity> getElementos()
   {
      return elementos;
   }

   /**
    * @param elementosNovos the elementosNovos to set
    */
   public void setElementos(List<ElementoIGTEntity> elementos)
   {
      this.elementos = elementos;
   }

   /**
    * @return the paiGeral
    */
   public PaiDTO getPaiGeral()
   {
      return paiGeral;
   }

   /**
    * @param paiGeral the paiGeral to set
    */
   public void setPaiGeral(PaiDTO paiGeral)
   {
      this.paiGeral = paiGeral;
   }

   /**
    * @return the pai
    */
   public PaiDTO getPai()
   {
      return pai;
   }

   /**
    * @param pai the pai to set
    */
   public void setPai(PaiDTO pai)
   {
      this.pai = pai;
   }

}
