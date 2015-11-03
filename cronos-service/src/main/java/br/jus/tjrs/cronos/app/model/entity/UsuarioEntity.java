package br.jus.tjrs.cronos.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.jus.tjrs.cronos.app.model.domain.Permissoes;
import br.jus.tjrs.cronos.app.model.domain.SimNao;
import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "USUARIO", schema = "CRONOS")
@SequenceGenerator(name = "USUARIO_ID_GENERATOR", sequenceName = "SE_USUARIO", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
         @NamedQuery(name = "UsuarioEntity.findByPaiAndSituacao", query = "SELECT u FROM UsuarioEntity u WHERE u.pai.id = :idPai AND u.situacao = :situacao")
})
public class UsuarioEntity extends VersionedEntity<Long> implements LogicalExclusion
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_ID_GENERATOR")
   @Column(name = "PK_USUARIO_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "NOME", length = 100)
   private String nome;

   @Column(name = "LOGIN", length = 60)
   private String login;

   @Column(name = "CPF", nullable = false, length = 11)
   private String cpf;

   @Enumerated(EnumType.STRING)
   @Column(name = "MAGISTRADO", nullable = true, length = 1)
   private SimNao magistrado;

   @Enumerated(EnumType.STRING)
   @Column(name = "PERMISSOES", nullable = true, length = 1)
   private Permissoes permissoes;

   @ManyToOne
   @XmlTransient
   @JoinColumn(name = "PAI", insertable = false, updatable = false)
   private UsuarioEntity pai;

   @Column(name = "PAI")
   private Long idPai;

   @Enumerated(EnumType.STRING)
   @Column(name = "PADRAO", nullable = false, length = 1, updatable = false, insertable = false)
   private SimNao padrao;

   @Enumerated(EnumType.STRING)
   @Column(name = "ACEITA_TERMO", nullable = false, length = 1)
   private SimNao aceiteTermo;

   public Long getIdPai()
   {
      return idPai;
   }

   public void setIdPai(Long idPai)
   {
      this.idPai = idPai;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getCpf()
   {
      return cpf;
   }

   public void setCpf(String cpf)
   {
      this.cpf = cpf;
   }

   public SimNao getMagistrado()
   {
      return magistrado;
   }

   public void setMagistrado(SimNao magistrado)
   {
      this.magistrado = magistrado;
   }

   public SimNao getPadrao()
   {
      return padrao;
   }

   public void setPadrao(SimNao padrao)
   {
      this.padrao = padrao;
   }

   public Permissoes getPermissoes()
   {
      return permissoes;
   }

   public void setPermissoes(Permissoes permissoes)
   {
      this.permissoes = permissoes;
   }

   public UsuarioEntity getPai()
   {
      return pai;
   }

   public void setPai(UsuarioEntity pai)
   {
      this.pai = pai;
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

}
