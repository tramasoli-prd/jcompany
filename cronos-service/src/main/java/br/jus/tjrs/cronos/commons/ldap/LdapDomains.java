package br.jus.tjrs.cronos.commons.ldap;

public enum LdapDomains
{

   TJRS_2G("DC=2g,DC=tj,DC=rs");
   // TJRS_1GPOA("DC=1gpoa,DC=tj,DC=rs"),
   // TJRS_1GINT("DC=1gint,DC=tj,DC=rs");

   private String domain;

   LdapDomains(String domain)
   {
      this.domain = domain;
   }

   public String domain()
   {
      return domain;
   }

   public static String[] all()
   {
      LdapDomains[] values = values();
      String[] domains = new String[values.length];
      for (LdapDomains attribute : values)
      {
         domains[attribute.ordinal()] = attribute.domain;
      }
      return domains;
   }

}
