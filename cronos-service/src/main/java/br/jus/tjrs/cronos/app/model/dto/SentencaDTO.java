package br.jus.tjrs.cronos.app.model.dto;

import java.util.ArrayList;
import java.util.List;

import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.app.model.entity.CategoriaItemEntity;

public class SentencaDTO
{
   private Long id;
   private String numeroProcesso;
   private Long idGrupoTrabalho;
   private String sentencaGerada;
   private CategoriaItemEntity modelo;
   private SimNao finalizada;
   private String usuarioAtualizacao;
   private ProcessoDTO dadosProcesso;
   private List<CategoriaItemEntity> topicos = new ArrayList<CategoriaItemEntity>();

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
    * @return the numeroProcesso
    */
   public String getNumeroProcesso()
   {
      return numeroProcesso;
   }

   /**
    * @param numeroProcesso the numeroProcesso to set
    */
   public void setNumeroProcesso(String numeroProcesso)
   {
      this.numeroProcesso = numeroProcesso;
   }

   /**
    * @return the sentencaGerada
    */
   public String getSentencaGerada()
   {
      return sentencaGerada;
   }

   /**
    * @param sentencaGerada the sentencaGerada to set
    */
   public void setSentencaGerada(String sentencaGerada)
   {
      this.sentencaGerada = sentencaGerada;
   }

   /**
    * @return the modelo
    */
   public CategoriaItemEntity getModelo()
   {
      return modelo;
   }

   /**
    * @param modelo the modelo to set
    */
   public void setModelo(CategoriaItemEntity modelo)
   {
      this.modelo = modelo;
   }

   /**
    * @return the topicos
    */
   public List<CategoriaItemEntity> getTopicos()
   {
      return topicos;
   }

   /**
    * @param topicos the topicos to set
    */
   public void setTopicos(List<CategoriaItemEntity> topicos)
   {
      this.topicos = topicos;
   }

   /**
    * @return the finalizada
    */
   public SimNao getFinalizada()
   {
      return finalizada;
   }

   /**
    * @param finalizada the finalizada to set
    */
   public void setFinalizada(SimNao finalizada)
   {
      this.finalizada = finalizada;
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
    * @return the dadosProcesso
    */
   public ProcessoDTO getDadosProcesso()
   {
      return dadosProcesso;
   }

   /**
    * @param dadosProcesso the dadosProcesso to set
    */
   public void setDadosProcesso(ProcessoDTO dadosProcesso)
   {
      this.dadosProcesso = dadosProcesso;
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
