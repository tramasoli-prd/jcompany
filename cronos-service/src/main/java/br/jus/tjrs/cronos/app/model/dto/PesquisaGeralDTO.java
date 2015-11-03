package br.jus.tjrs.cronos.app.model.dto;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.domain.TipoLabel;

public class PesquisaGeralDTO
{

   String part;
   TipoArvore tipoArvore;
   Long idGrupoTrabalho;
   TipoLabel tipo;

   /**
    * @return the part
    */
   public String getPart()
   {
      return part;
   }

   /**
    * @param part the part to set
    */
   public void setPart(String part)
   {
      this.part = part;
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
    * @return the tipo
    */
   public TipoLabel getTipo()
   {
      return tipo;
   }

   /**
    * @param tipo the tipo to set
    */
   public void setTipo(TipoLabel tipo)
   {
      this.tipo = tipo;
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
