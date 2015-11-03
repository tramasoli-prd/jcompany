package br.jus.tjrs.cronos.app.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.core.model.entity.LogicalExclusion;
import br.jus.tjrs.cronos.core.model.entity.VersionedEntity;

@Entity
@Table(name = "TOKEN", schema = "CRONOS")
@SequenceGenerator(name = "TOKEN_GENERATOR", sequenceName = "SEQ_TOKEN", allocationSize = 1, schema = "CRONOS")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TokenEntity extends VersionedEntity<Long> implements LogicalExclusion
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_GENERATOR")
   @Column(name = "PK_TOKEN_01", unique = true, nullable = false, precision = 10)
   private Long id;

   @Column(name = "LABEL")
   private String label;

   @Column(name = "nome")
   private String nome;

   @Column(name = "TABELA")
   private String tabela;

   @Column(name = "CAMPO")
   private String campo;

   @Column(name = "FUNCAO")
   private String funcao;

   @Column(name = "METODO")
   private String metodo;

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
    * @return the label
    */
   public String getLabel()
   {
      return label;
   }

   /**
    * @param label the label to set
    */
   public void setLabel(String label)
   {
      this.label = label;
   }

   /**
    * @return the tabela
    */
   public String getTabela()
   {
      return tabela;
   }

   /**
    * @param tabela the tabela to set
    */
   public void setTabela(String tabela)
   {
      this.tabela = tabela;
   }

   /**
    * @return the campo
    */
   public String getCampo()
   {
      return campo;
   }

   /**
    * @param campo the campo to set
    */
   public void setCampo(String campo)
   {
      this.campo = campo;
   }

   /**
    * @return the funcao
    */
   public String getFuncao()
   {
      return funcao;
   }

   /**
    * @param funcao the funcao to set
    */
   public void setFuncao(String funcao)
   {
      this.funcao = funcao;
   }

   /**
    * @return the metodo
    */
   public String getMetodo()
   {
      return metodo;
   }

   /**
    * @param metodo the metodo to set
    */
   public void setMetodo(String metodo)
   {
      this.metodo = metodo;
   }

   /**
    * @return the nome
    */
   public String getNome()
   {
      return nome;
   }

   /**
    * @param nome the nome to set
    */
   public void setNome(String nome)
   {
      this.nome = nome;
   }

}
