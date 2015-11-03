package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;

public class SaveNodeDTO
{
   String titulo;
   Long idGrupoTrabalho;
   String usuarioAtualizacao;
   TipoArvore tipoArvore;

   /**
    * @return the titulo
    */
   public String getTitulo()
   {
      return titulo;
   }

   /**
    * @param titulo the titulo to set
    */
   public void setTitulo(String titulo)
   {
      this.titulo = titulo;
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

   /**
    * @return the tipoArvore
    */
   public TipoArvore getTipoArvore()
   {
      return tipoArvore;
   }

   /**
    * @param tipoArvore the tipoArvore to set
    */
   public void setTipoArvore(TipoArvore tipoArvore)
   {
      this.tipoArvore = tipoArvore;
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

}
