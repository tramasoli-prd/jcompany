package br.jus.tjrs.cronos.app.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.app.model.domain.Permissoes;
import br.jus.tjrs.cronos.app.model.domain.SimNao;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioDTO
{

   private Long id;
   private String cpf;
   private String nome;
   private SimNao magistrado;
   private Permissoes permissoes;
   private Long idPai;
   private int versao;
   private String login;
   private SimNao aceiteTermo;
   private Long idGrupoTrabalho;
   private String usuarioAtualizacao;

   public String getCpf()
   {
      return cpf;
   }

   public void setCpf(String cpf)
   {
      this.cpf = cpf;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public SimNao getMagistrado()
   {
      return magistrado;
   }

   public void setMagistrado(SimNao magistrado)
   {
      this.magistrado = magistrado;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Permissoes getPermissoes()
   {
      return permissoes;
   }

   public void setPermissoes(Permissoes permissoes)
   {
      this.permissoes = permissoes;
   }

   public Long getIdPai()
   {
      return idPai;
   }

   public void setIdPai(Long idPai)
   {
      this.idPai = idPai;
   }

   public int getVersao()
   {
      return versao;
   }

   public void setVersao(int versao)
   {
      this.versao = versao;
   }

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   /**
    * @return the aceiteTermo
    */
   public SimNao getAceiteTermo()
   {
      return aceiteTermo;
   }

   /**
    * @param aceiteTermo the aceiteTermo to set
    */
   public void setAceiteTermo(SimNao aceiteTermo)
   {
      this.aceiteTermo = aceiteTermo;
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
