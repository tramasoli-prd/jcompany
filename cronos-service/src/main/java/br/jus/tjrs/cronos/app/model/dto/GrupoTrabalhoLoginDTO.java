package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.SimNao;

public class GrupoTrabalhoLoginDTO
{
   private Long idGrupoTrabalho;
   private String nomeMagistrado;
   private SimNao padrao;
   private Long idUsuario;

   /**
    * @return the idGrupoTrabalho
    */
   public Long getIdGrupoTrabalho()
   {
      return idGrupoTrabalho;
   }

   /**
    * @param idGrupoTrabalho the idGrupoTrabalho to set
    */
   public void setIdGrupoTrabalho(Long idGrupoTrabalho)
   {
      this.idGrupoTrabalho = idGrupoTrabalho;
   }

   /**
    * @return the nomeMagistrado
    */
   public String getNomeMagistrado()
   {
      return nomeMagistrado;
   }

   /**
    * @param nomeMagistrado the nomeMagistado to set
    */
   public void setNomeMagistrado(String nomeMagistrado)
   {
      this.nomeMagistrado = nomeMagistrado;
   }

   /**
    * @return the padrao
    */
   public SimNao getPadrao()
   {
      return padrao;
   }

   /**
    * @param padrao the padrao to set
    */
   public void setPadrao(SimNao padrao)
   {
      this.padrao = padrao;
   }

   /**
    * @return the idUsuario
    */
   public Long getIdUsuario()
   {
      return idUsuario;
   }

   /**
    * @param idUsuario the idUsuario to set
    */
   public void setIdUsuario(Long idUsuario)
   {
      this.idUsuario = idUsuario;
   }

}
