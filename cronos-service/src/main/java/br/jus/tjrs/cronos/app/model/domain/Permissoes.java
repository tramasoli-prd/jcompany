package br.jus.tjrs.cronos.app.model.domain;

public enum Permissoes
{

   R /* simNao.R=READY (LEITURA) */,
   W /* simNao.W=WRITE (ESCRITA) */,
   A /* simNao.A=ADMINISTRATION (ADMINISTRACAO) */;

   /**
    * @return Retorna o codigo.
    */
   public String getCodigo()
   {
      return this.toString();
   }

}
