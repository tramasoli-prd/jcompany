package br.jus.tjrs.cronos.app.model.domain;

/**
 * Enum que representa a situa��o do registro na base de dados.
 */
public enum Situacao
{

   A /* situacao.A=Ativo */,
   I /* situacao.I=Inativo */,
   E /* situacao.E=Excluido */;

   /**
    * @return Retorna o codigo.
    */
   public String getCodigo()
   {
      return this.toString();
   }

}
