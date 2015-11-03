package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.SimNao;

public class ConfiguracaoDTO
{
   private SimNao permiteEditarMenu;
   private Long idGrupoTrabalho;
   private String usuarioAtualizacao;

   /**
    * @return the permiteEditarMenu
    */
   public SimNao getPermiteEditarMenu()
   {
      return permiteEditarMenu;
   }

   /**
    * @param permiteEditarMenu the permiteEditarMenu to set
    */
   public void setPermiteEditarMenu(SimNao permiteEditarMenu)
   {
      this.permiteEditarMenu = permiteEditarMenu;
   }

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
    * @return the usuarioAtualizacao
    */
   public String getUsuarioAtualizacao()
   {
      return usuarioAtualizacao;
   }

   /**
    * @param usuarioAtualizacao the usuarioAtualizacao to set
    */
   public void setUsuarioAtualizacao(String usuarioAtualizacao)
   {
      this.usuarioAtualizacao = usuarioAtualizacao;
   }

}
