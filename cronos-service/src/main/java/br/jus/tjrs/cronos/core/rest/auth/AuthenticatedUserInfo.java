package br.jus.tjrs.cronos.core.rest.auth;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.dto.UsuarioDTO;
import br.jus.tjrs.cronos.app.util.ConstantUtil;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthenticatedUserInfo implements Serializable
{
   private static final long serialVersionUID = 1L;

   public static final String PROPERTY = AuthenticatedUserInfo.class.getName();

   private String username;
   private String cpf;
   private String name;
   private String email;
   private boolean magistrado;
   private UsuarioDTO usuario;
   private Long idGrupoTrabalho;

   private Set<String> roles;

   @XmlTransient
   private String host;

   public AuthenticatedUserInfo()
   {
      super();
   }

   public AuthenticatedUserInfo(String username, String host, Set<String> roles)
   {
      this.username = username.toLowerCase();
      this.host = host;
      this.roles = roles;
      for (String string : roles)
      {
         if (string.contains(ConstantUtil.ROLE_MAGISTRATE))
         {
            this.magistrado = true;
         }
      }
   }

   public boolean checkHost(String host)
   {
      return host != null && host.equals(this.host);
   }

   public String getUsername()
   {
      return username;
   }

   public Set<String> getRoles()
   {
      return roles;
   }

   @XmlTransient
   public String getHost()
   {
      return host;
   }

   public String getCpf()
   {
      return cpf;
   }

   public void setCpf(String cpf)
   {
      this.cpf = cpf;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public boolean isMagistrado()
   {
      return magistrado;
   }

   public UsuarioDTO getUsuario()
   {
      return usuario;
   }

   public void setUsuario(UsuarioDTO usuario)
   {
      this.usuario = usuario;
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
