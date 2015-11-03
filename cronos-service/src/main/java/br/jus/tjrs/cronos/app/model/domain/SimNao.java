package br.jus.tjrs.cronos.app.model.domain;

public enum SimNao
{

   S /* simNao.S=Sim */,
   N /* simNao.N=Não */;

   /**
    * @return Retorna o codigo.
    */
   public String getCodigo()
   {
      return this.toString();
   }

}
