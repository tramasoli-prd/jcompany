package br.jus.tjrs.cronos.app.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.jus.tjrs.cronos.app.model.domain.TipoArvore;
import br.jus.tjrs.cronos.app.model.domain.TipoNodo;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractNodeDTO implements Comparable<AbstractNodeDTO>
{

   private Long id;

   private String label;

   private String icone;

   private TipoNodo tipo;

   private Integer ordem;

   private TipoArvore tipoArvore;

   private Long idIcone;

   private Long idNodo;

   private String iconeCor;

   private List<AbstractNodeDTO> itens = new ArrayList<AbstractNodeDTO>();

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public List<AbstractNodeDTO> getItens()
   {
      return itens;
   }

   public String getLabel()
   {
      return label;
   }

   public void setLabel(String label)
   {
      this.label = label;
   }

   public String getIcone()
   {
      return icone;
   }

   public void setIcone(String icone)
   {
      this.icone = icone;
   }

   public TipoNodo getTipo()
   {
      return tipo;
   }

   public void setTipo(TipoNodo tipo)
   {
      this.tipo = tipo;
   }

   public Integer getOrdem()
   {
      return ordem;
   }

   public void setOrdem(Integer ordem)
   {
      this.ordem = ordem;
   }

   public TipoArvore getTipoArvore()
   {
      return tipoArvore;
   }

   public void setTipoArvore(TipoArvore tipoArvore)
   {
      this.tipoArvore = tipoArvore;
   }

   /**
    * @return the idIcone
    */
   public Long getIdIcone()
   {
      return idIcone;
   }

   /**
    * @param idIcone the idIcone to set
    */
   public void setIdIcone(Long idIcone)
   {
      this.idIcone = idIcone;
   }

   /**
    * @return the iconeCor
    */
   public String getIconeCor()
   {
      return iconeCor;
   }

   /**
    * @param iconeCor the iconeCor to set
    */
   public void setIconeCor(String iconeCor)
   {
      this.iconeCor = iconeCor;
   }

   /**
    * @return the idPai
    */
   public Long getIdNodo()
   {
      return idNodo;
   }

   /**
    * @param idPai the idPai to set
    */
   public void setIdNodo(Long idNodo)
   {
      this.idNodo = idNodo;
   }

   @Override
   public int compareTo(AbstractNodeDTO abstrato)
   {
      if (this.getOrdem() != null || abstrato.getOrdem() != null)
      {
         return this.getOrdem().compareTo(abstrato.getOrdem());
      }
      if (this.getOrdem() != null)
      {
         return 1;
      }
      if (abstrato.getOrdem() != null)
      {
         return -1;
      }
      return 0;
   }

}
