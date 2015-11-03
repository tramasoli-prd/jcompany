package br.jus.tjrs.cronos.app.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ProcessoDTO
{

   String numeroCNJ;
   String numeroFormatado;
   String classeCNJ;
   String assuntoCNJ;
   String vara;
   String juizado;
   String codigoComarca;
   String comarca;
   String valorAcao;
   List<ParteDTO> reu = new ArrayList<ParteDTO>();
   List<ParteDTO> autor = new ArrayList<ParteDTO>();

   /**
    * @return the numeroCNJ
    */
   public String getNumeroCNJ()
   {
      return numeroCNJ;
   }

   /**
    * @param numeroCNJ the numeroCNJ to set
    */
   public void setNumeroCNJ(String numeroCNJ)
   {
      this.numeroCNJ = numeroCNJ;
   }

   /**
    * @return the classeCNJ
    */
   public String getClasseCNJ()
   {
      return classeCNJ;
   }

   /**
    * @param classeCNJ the classeCNJ to set
    */
   public void setClasseCNJ(String classeCNJ)
   {
      this.classeCNJ = classeCNJ;
   }

   /**
    * @return the assuntoCNJ
    */
   public String getAssuntoCNJ()
   {
      return assuntoCNJ;
   }

   /**
    * @param assuntoCNJ the assuntoCNJ to set
    */
   public void setAssuntoCNJ(String assuntoCNJ)
   {
      this.assuntoCNJ = assuntoCNJ;
   }

   /**
    * @return the vara
    */
   public String getVara()
   {
      return vara;
   }

   /**
    * @param vara the vara to set
    */
   public void setVara(String vara)
   {
      this.vara = vara;
   }

   /**
    * @return the juizado
    */
   public String getJuizado()
   {
      return juizado;
   }

   /**
    * @param juizado the juizado to set
    */
   public void setJuizado(String juizado)
   {
      this.juizado = juizado;
   }

   /**
    * @return the codigoComarca
    */
   public String getCodigoComarca()
   {
      return codigoComarca;
   }

   /**
    * @param codigoComarca the codigoComarca to set
    */
   public void setCodigoComarca(String codigoComarca)
   {
      this.codigoComarca = codigoComarca;
   }

   /**
    * @return the valorAcao
    */
   public String getValorAcao()
   {
      return valorAcao;
   }

   /**
    * @param valorAcao the valorAcao to set
    */
   public void setValorAcao(String valorAcao)
   {
      this.valorAcao = valorAcao;
   }

   /**
    * @return the reu
    */
   public List<ParteDTO> getReu()
   {
      return reu;
   }

   /**
    * @param reu the reu to set
    */
   public void setReu(List<ParteDTO> reu)
   {
      this.reu = reu;
   }

   /**
    * @return the autor
    */
   public List<ParteDTO> getAutor()
   {
      return autor;
   }

   /**
    * @param autor the autor to set
    */
   public void setAutor(List<ParteDTO> autor)
   {
      this.autor = autor;
   }

   /**
    * @return the comarca
    */
   public String getComarca()
   {
      return comarca;
   }

   /**
    * @param comarca the comarca to set
    */
   public void setComarca(String comarca)
   {
      this.comarca = comarca;
   }

   /**
    * @return the numeroFormatado
    */
   public String getNumeroFormatado()
   {
      return numeroFormatado;
   }

   /**
    * @param numeroFormatado the numeroFormatado to set
    */
   public void setNumeroFormatado(String numeroFormatado)
   {
      this.numeroFormatado = numeroFormatado;
   }

}
