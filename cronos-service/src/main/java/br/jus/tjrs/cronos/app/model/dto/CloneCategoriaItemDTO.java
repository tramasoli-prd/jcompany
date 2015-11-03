package br.jus.tjrs.cronos.app.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CloneCategoriaItemDTO
{

   private Long id;
   private Long idGrupoTrabalho;
   private String usuarioAtualizacao;

   /**
    * @return the id
    */
   public Long getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(Long id)
   {
      this.id = id;
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
