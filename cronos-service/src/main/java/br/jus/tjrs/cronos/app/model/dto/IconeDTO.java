package br.jus.tjrs.cronos.app.model.dto;

public class IconeDTO
{
   Long id;
   String icone;
   String cor;
   ImagemDTO imagem;
   String usuarioAtualizacao;

   /**
    * @return the idTaxonomia
    */
   public Long getId()
   {
      return id;
   }

   /**
    * @param idTaxonomia the idTaxonomia to set
    */
   public void setId(Long id)
   {
      this.id = id;
   }

   /**
    * @return the icone
    */
   public String getIcone()
   {
      return icone;
   }

   /**
    * @param icone the icone to set
    */
   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   /**
    * @return the cor
    */
   public String getCor()
   {
      return cor;
   }

   /**
    * @param cor the cor to set
    */
   public void setCor(String cor)
   {
      this.cor = cor;
   }

   /**
    * @return the imagem
    */
   public ImagemDTO getImagem()
   {
      return imagem;
   }

   /**
    * @param imagem the imagem to set
    */
   public void setImagem(ImagemDTO imagem)
   {
      this.imagem = imagem;
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
