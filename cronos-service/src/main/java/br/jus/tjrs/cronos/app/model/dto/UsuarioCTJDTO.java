package br.jus.tjrs.cronos.app.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.app.model.domain.SimNao;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioCTJDTO
{

   private String cpf;
   private String nome;
   private SimNao magistrado;

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

}
