package br.jus.tjrs.cronos.commons.ldap;

public enum LdapAttributes
{

   NOME("name"),
   CPF("extensionAttribute3"),
   LOGIN("sAMAccountName"),
   EMAIL("mail"),
   GRUPOS("memberOf");

   private String key;

   private LdapAttributes(String key)
   {
      this.key = key;
   }

   public String[] keyToArray()
   {
      return new String[] { this.key };
   }

   public String key()
   {
      return this.key;
   }

   public static String[] all()
   {
      LdapAttributes[] values = values();
      String[] keys = new String[values.length];
      for (LdapAttributes attribute : values)
      {
         keys[attribute.ordinal()] = attribute.key;
      }
      return keys;
   }

}