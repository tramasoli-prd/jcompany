package br.jus.tjrs.cronos.core.rest.auth;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.security.auth.Subject;

import org.apache.commons.lang3.StringUtils;

import br.jus.tjrs.cronos.commons.ldap.ActiveDirectoryUtils;
import br.jus.tjrs.cronos.commons.ldap.LdapAttributes;
import br.jus.tjrs.cronos.core.CronosException;

@ApplicationScoped
public class WeblogicIntegration
{

   public Set<String> getRoles()
   {
      Set<String> roles = new TreeSet<>();
      try
      {
         Subject subject = getWLSubject();

         for (Principal principal : subject.getPrincipals())
         {
            // Weblogic Group is a Role!
            if (principal.getClass().getSimpleName().contains("WLSGroup"))
            {
               roles.add(principal.getName().toUpperCase());
            }
         }
      }
      catch (Exception e)
      {
      }
      return roles;
   }

   private Subject getWLSubject()
   {
      try
      {
         return (Subject) Class.forName("weblogic.security.Security").getMethod("getCurrentSubject").invoke(null);
      }
      catch (Exception e)
      {
         return null;
      }
   }

   public boolean loadExtraInfo(AuthenticatedUserInfo userInfo, final Principal userPrincipal) throws CronosException
   {
      final ActiveDirectoryUtils activeDirectoryUtils = new ActiveDirectoryUtils();

      boolean fieldRequired = true;
      Map<LdapAttributes, String> attributes = new HashMap<LdapAttributes, String>(); // activeDirectoryUtils.getUserAttributes(userPrincipal);
      attributes.put(LdapAttributes.NOME, "Admin");
      attributes.put(LdapAttributes.EMAIL, "admin@powerlogic.com.br");
      attributes.put(LdapAttributes.CPF, "12345678909");
      if (attributes != null)
      {
         final String nome = attributes.get(LdapAttributes.NOME);
         final String email = attributes.get(LdapAttributes.EMAIL);
         final String cpf = attributes.get(LdapAttributes.CPF);

         if (StringUtils.isNotBlank(nome))
         {
            userInfo.setName(nome);
         }
         else
         {
            fieldRequired = false;
         }
         if (StringUtils.isNotBlank(email))
         {
            userInfo.setEmail(email);
         }

         if (userInfo.getUsername().equalsIgnoreCase("et1g-magi_frederico"))
         {
            userInfo.setCpf("11111111111");
         }
         else if (userInfo.getUsername().equalsIgnoreCase("et1g-magi_igrejinha"))
         {
            userInfo.setCpf("22222222222");
         }
         else if (userInfo.getUsername().equalsIgnoreCase("et1g-magi_montenegro"))
         {
            userInfo.setCpf("33333333333");
         }
         else if (userInfo.getUsername().equalsIgnoreCase("et1g-magi_partenon"))
         {
            userInfo.setCpf("44444444444");
         }
         else
         {
            if (StringUtils.isNotBlank(cpf))
            {
               userInfo.setCpf(cpf);
            }
            else
            {
               fieldRequired = false;
            }
         }
      }
      else
      {
         fieldRequired = false;
      }
      return fieldRequired;
   }

}
