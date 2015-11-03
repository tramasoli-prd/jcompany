package br.jus.tjrs.cronos.commons.ldap;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.jus.tjrs.cronos.app.exception.CronosErrors;
import br.jus.tjrs.cronos.app.util.ConstantUtil;
import br.jus.tjrs.cronos.core.CronosException;

/**
 * Classe Responsável por fazer a comunicação com o Active Directory
 * 
 * @author Marcelo Goncalves Moraes (mgmoraes@tj.rs.gov.br)
 *
 */
public class ActiveDirectoryUtils
{

   public static final Logger LOG = Logger.getLogger(ActiveDirectoryUtils.class);

   /**
    * Regex para extrair o grupo do usuario
    */
   public static final String REGEX_GROUP = "CN=|,(.+)";

   /**
    * Base de todos os usuario do Ldap
    */
   public static final String LDAP_SEARCH_BASE = "OU=Delegados";

   /**
    * Vari�vel utilizada em mais de um substring dentro da classe.
    */
   public static final String SUB_DC = ",DC";

   /**
    * Query para retornar o Objeto que representa o Usuario no Ldap. Parametro de busca sAMAccountName = login do
    * usuario
    */
   public static final String QUERY_FIND_BY_LOGIN = "(&(sAMAccountName=%s)(objectclass=user))";

   /**
    * Query para retornar o Objeto que representa o Usuario no Ldap. Parametro de busca sAMAccountName = cpf do usuario
    */
   public static final String QUERY_FIND_BY_CPF = "(&(extensionAttribute3=%s)(objectclass=user))";

   /**
    * @param domain JNDI do provedor Ldap registrado no Weblogic
    * @return {@link DirContext} contexto Ldap
    */
   private DirContext lookupLdapContext(String domain)
   {
      try
      {
         Object root = InitialContext.doLookup(domain);
         if (root instanceof DirContext)
         {
            return (DirContext) root;
         }
         else
         {
            throw new NamingException("JNDI not instanceof javax.naming.directory.DirContext");
         }
      }
      catch (NamingException e)
      {
         LOG.error("Erro JNDI:" + domain, e);
         throw new WebApplicationException(e);
      }
   }

   /**
    * @param attribute Atributo de um Objeto do Ldap para retornar seu valor em String
    * @return {@link String} valor do atributo
    */
   private String getAttributeValue(Attribute attribute) throws NamingException
   {
      return attribute != null ? Objects.toString(attribute.get()) : ConstantUtil.EMPTY;
   }

   /**
    * @param ctx Contexto da JNDI que representa o Ldap
    * @param ldapSearchBase OU de base da pesquisa
    * @param searchFilter LdapQuery para realizar o filtro
    * @param returningAttributes Atributos do objeto Ldap que deseja retornar na pesquisa
    * @return Enumeration of {@link SearchResult}
    */
   private Enumeration<SearchResult> ldapSearch(DirContext ctx, String ldapSearchBase, String searchFilter,
            String... returningAttributes)
   {
      SearchControls searchControls = new SearchControls();
      searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
      searchControls.setReturningAttributes(returningAttributes);
      Enumeration<SearchResult> results;
      try
      {
         results = ctx.search(ldapSearchBase, searchFilter, searchControls);
         return results;
      }
      catch (Exception e)
      {
         LOG.error("Erro search on ldap, dn:" + ldapSearchBase + ", Filter:" + searchFilter, e);
      }
      return null;
   }

   /**
    * @param ldapSearchBase OU de base da pesquisa
    * @param searchFilter LdapQuery para realizar o filtro
    * @param attribute Atributo do objeto Ldap que deseja retornar na pesquisa
    * @return valor do atributo do objeto no Ldap {@link String}
    */
   @SuppressWarnings("unchecked")
   private List<String> findLdapAttribute(final String ldapSearchBase, final String searchFilter,
            final LdapAttributes attribute)
   {
      List<String> result = null;
      for (String domain : LdapDomains.all())
      {
         try
         {
            DirContext ctx = InitialContext.doLookup(domain);
            Enumeration<SearchResult> resultSet = ldapSearch(ctx, ldapSearchBase, searchFilter, attribute.key());
            if (resultSet != null && resultSet.hasMoreElements())
            {
               Attributes attributes = resultSet.nextElement().getAttributes();
               if (attributes != null)
               {
                  Attribute attr = attributes.get(attribute.key());
                  NamingEnumeration<String> all = (NamingEnumeration<String>) attr.getAll();
                  result = Collections.list(all);
               }
            }
         }
         catch (Exception e)
         {
            LOG.error("Erro search on ldap, dn:" + ldapSearchBase + ", Filter:" + searchFilter + "Attribute:"
                     + attribute, e);
         }
      }
      return result;
   }

   /**
    * @param accountName login do usuario
    * @return Lista com todos os grupos do usuario
    */
   public List<String> findGruposByLogin(final String accountName)
   {
      List<String> filtered = new ArrayList<String>();
      String query = String.format(QUERY_FIND_BY_LOGIN, accountName);
      List<String> result = findLdapAttribute(LDAP_SEARCH_BASE, query, LdapAttributes.GRUPOS);
      if (result != null && result.size() > 0)
      {
         for (String ldapGroup : result)
         {
            filtered.add(ldapGroup.replaceFirst(REGEX_GROUP, ConstantUtil.EMPTY));
         }
      }
      return filtered;
   }

   /**
    * @param accountName login do usuario
    * @return nome completo do usuario {@link String}
    */
   public String findNomeByLogin(final String accountName)
   {
      String query = String.format(QUERY_FIND_BY_LOGIN, accountName);
      List<String> result = findLdapAttribute(LDAP_SEARCH_BASE, query, LdapAttributes.NOME);
      return result != null && result.size() > 0 ? result.get(0) : ConstantUtil.EMPTY;
   }

   /**
    * @param accountName login do usuario
    * @return CPF do usuario {@link String}
    */
   public String findCPFByLogin(final String accountName)
   {
      String query = String.format(QUERY_FIND_BY_LOGIN, accountName);
      List<String> result = findLdapAttribute(LDAP_SEARCH_BASE, query, LdapAttributes.CPF);
      return result != null && result.size() > 0 ? result.get(0) : ConstantUtil.EMPTY;
   }

   /**
    * @param cpf CPF do usuario
    * @return login do usuario {@link String}
    */
   public String findLoginByCPF(final String cpf)
   {
      String query = String.format(QUERY_FIND_BY_CPF, cpf);
      List<String> result = findLdapAttribute(LDAP_SEARCH_BASE, query, LdapAttributes.LOGIN);
      return result != null && result.size() > 0 ? result.get(0) : ConstantUtil.EMPTY;
   }

   /**
    * @param user instacia de WLSUserImpl que contem o atributo Distinguished Name (DN) do usuario logado
    * @return atributo {@link LdapAttributes#CPF} que representa o CPF do usuario no (AD)
    */
   public String getCPF(final Principal user)
   {
      String dn = getDn(user);
      if (dn == null)
      {
         return ConstantUtil.EMPTY;
      }
      return getCPF(dn);
   }

   /**
    * @param user instacia de WLSUserImpl que contem o atributo Distinguished Name (DN) do usuario logado
    * @return atributo {@value LdapAttributes#NOME} que representa o NOME do usuario no (AD)
    */
   public String getNome(final Principal user)
   {
      String dn = getDn(user);
      if (dn == null)
      {
         return ConstantUtil.EMPTY;
      }
      return getNome(dn);
   }

   /**
    * @param user instacia de WLSUserImpl que contem o atributo Distinguished Name (DN) do usuario logado
    * @return atributo {@value LdapAttributes#NOME} que representa o NOME do usuario no (AD)
    */
   public String getEmail(final Principal user)
   {
      String dn = getDn(user);
      if (dn == null)
      {
         return ConstantUtil.EMPTY;
      }
      return getEmail(dn);
   }

   /**
    * @param user instacia de WLSUserImpl que contem o atributo Distinguished Name (DN) do usuario logado
    * @return atributo {@value LdapAttributes#NOME} que representa o NOME do usuario no (AD)
    */
   public String getLogin(final Principal user)
   {
      String dn = getDn(user);
      if (dn == null)
      {
         return ConstantUtil.EMPTY;
      }
      return getLogin(dn);
   }

   /**
    * @param user instacia de WLSUserImpl que contem o atributo Distinguished Name (DN) do usuario logado
    * @return atributo {@value LdapAttributes#NOME} que representa o NOME do usuario no (AD)
    * @throws CronosException
    */
   public Map<LdapAttributes, String> getUserAttributes(final Principal user) throws CronosException
   {
      String dn = getDn(user);
      if (dn == null)
      {
         return Collections.emptyMap();
      }
      return getLdapAttributes(dn);
   }

   /**
    * @param dn Distinguished Name (DN) do usuario, que deve representar o caminho completo de um objeto User no (AD)
    * 
    *           <pre>
    * ex: dn="CN=Charles Heidemann,OU=Analistas de Sistemas,OU=Serviço de Sistemas,OU=Departamento de Informática,OU=Administrativo,OU=Delegados,DC=2g,DC=tj,DC=rs"
    * </pre>
    * 
    *           Descobre o Domain Component (DC) que representa a raiz da arvore do AD e sera utilizado para encontrar a
    *           JNDI do (AD) correspondente no WLS
    * 
    *           <pre>
    * ex: dc="DC=2g,DC=tj,DC=rs"
    * </pre>
    * 
    *           E o Common Name (CN) do User, que sera utilizado para localizar o objeto User, no (AD) correspondente ao
    *           (DC), apartir de sua raiz
    * 
    *           <pre>
    * ex: cn="CN=Charles Heidemann,OU=Analistas de Sistemas,OU=Serviço de Sistemas,OU=Departamento de Informática,OU=Administrativo,OU=Delegados"
    * </pre>
    * @return atributo {@link LdapAttributes#CPF} que representa o CPF do usuario no (AD)
    */
   public String getCPF(final String dn)
   {
      String cn = StringUtils.substringBefore(dn, SUB_DC);
      String dc = StringUtils.substringAfter(dn, cn.concat(ConstantUtil.COMMA));
      return getAttribute(cn, dc, LdapAttributes.CPF);
   }

   /**
    * @param dn Distinguished Name (DN) do usuario, que deve representar o caminho completo de um objeto User no (AD)
    * 
    * @return atributo {@link LdapAttributes#NOME} que representa o NOME do usuario no (AD)
    * 
    * @see #getCPF(String)
    */
   public String getNome(final String dn)
   {
      String cn = StringUtils.substringBefore(dn, SUB_DC);
      String dc = StringUtils.substringAfter(dn, cn.concat(ConstantUtil.COMMA));
      return getAttribute(cn, dc, LdapAttributes.NOME);
   }

   /**
    * @param dn Distinguished Name (DN) do usuario, que deve representar o caminho completo de um objeto User no (AD)
    * 
    * @return atributo {@link LdapAttributes#EMAIL} que representa o EMAIL do usuario no (AD)
    * 
    * @see #getCPF(String)
    */
   public String getEmail(final String dn)
   {
      String cn = StringUtils.substringBefore(dn, SUB_DC);
      String dc = StringUtils.substringAfter(dn, cn.concat(ConstantUtil.COMMA));
      return getAttribute(cn, dc, LdapAttributes.EMAIL);
   }

   /**
    * @param dn Distinguished Name (DN) do usuario, que deve representar o caminho completo de um objeto User no (AD)
    * 
    * @return atributo {@link LdapAttributes#LOGIN} que representa o LOGIN do usuario no (AD)
    * 
    * @see #getCPF(String)
    */
   public String getLogin(final String dn)
   {
      String cn = StringUtils.substringBefore(dn, SUB_DC);
      String dc = StringUtils.substringAfter(dn, cn.concat(ConstantUtil.COMMA));
      return getAttribute(cn, dc, LdapAttributes.LOGIN);
   }

   /**
    * @param cn Common Name (CN) do User, que sera utilizado para localizar o objeto User, no (AD) correspondente ao
    *           (DC), apartir de sua raiz
    * 
    * @return uma String com a valor do atributo no (AD)
    * 
    * @see #getCPF(String)
    */
   protected String getAttribute(final String cn, final String dc, final LdapAttributes attr)
   {
      String result = ConstantUtil.EMPTY;
      try
      {
         DirContext root = lookupLdapContext(dc);
         Attributes attributes = root.getAttributes(cn, attr.keyToArray());
         Attribute attribute = attributes.get(attr.key());
         result = getAttributeValue(attribute);
      }
      catch (Exception e)
      {
         LOG.error("Erro JNDI:" + dc, e);
      }
      return result;
   }

   /**
    * @param user instacia de WLSUserImpl que contem o atributo Distinguished Name (DN) do usuario logado
    * @return (DN) do usuario logado
    */
   protected String getDn(final Principal user)
   {
      String dn = null;
      try
      {
         dn = Objects.toString(user.getClass().getMethod("getDn").invoke(user));
      }
      catch (Exception e)
      {
         LOG.error("Usuario logado nao possui informacoes do AD", e);
      }
      return dn;
   }

   /**
    * @param dn Distinguished Name (DN) do usuario, que deve representar o caminho completo de um objeto User no (AD)
    * 
    * @return map with all {@link LdapAttributes}
    * @throws CronosException
    */
   public Map<LdapAttributes, String> getLdapAttributes(final String dn) throws CronosException
   {
      String cn = StringUtils.substringBefore(dn, SUB_DC);
      String dc = StringUtils.substringAfter(dn, cn.concat(ConstantUtil.COMMA));
      return getLdapAttributes(cn, dc);
   }

   /**
    * * @param cn Common Name (CN) do User, que sera utilizado para localizar o objeto User, no (AD) correspondente ao
    * (DC), apartir de sua raiz
    * 
    * @return map with all {@link LdapAttributes}
    * @throws CronosException
    */
   protected Map<LdapAttributes, String> getLdapAttributes(final String cn, final String dc) throws CronosException
   {
      try
      {
         DirContext root = lookupLdapContext(dc);
         return getLdapAttributes(root.getAttributes(cn, LdapAttributes.all()));
      }
      catch (Exception e)
      {
         LOG.error("Erro JNDI:" + dc, e);
         throw CronosErrors.FALHA_OPERACAO_009.create();
      }
   }

   /**
    * @return Map with all {@link LdapAttributes} in {@link Attributes}.
    */
   protected Map<LdapAttributes, String> getLdapAttributes(final Attributes attributes) throws NamingException
   {
      Map<LdapAttributes, String> map = new HashMap<LdapAttributes, String>();
      for (LdapAttributes att : LdapAttributes.values())
      {
         Attribute attribute = attributes.get(att.key());
         if (attribute != null)
         {
            map.put(att, Objects.toString(attribute.get()));
         }
      }
      return map;
   }

}
