package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.TipoParte;

public class ParteDTO
{
   String nome;
   TipoParte tipo;

   /**
    * @return the nome
    */
   public String getNome()
   {
      return nome;
   }

   /**
    * @param nome the nome to set
    */
   public void setNome(String nome)
   {
      this.nome = nome;
   }

   /**
    * @return the tipo
    */
   public TipoParte getTipo()
   {
      return tipo;
   }

   /**
    * @param tipo the tipo to set
    */
   public void setTipo(TipoParte tipo)
   {
      this.tipo = tipo;
   }
}
