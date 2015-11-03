package br.jus.tjrs.cronos.app.model.dto;

public class ImagemDTO
{
   Integer filesize;
   String filetype;
   String filename;
   String base64;

   /**
    * @return the filesize
    */
   public Integer getFilesize()
   {
      return filesize;
   }

   /**
    * @param filesize the filesize to set
    */
   public void setFilesize(Integer filesize)
   {
      this.filesize = filesize;
   }

   /**
    * @return the filetype
    */
   public String getFiletype()
   {
      return filetype;
   }

   /**
    * @param filetype the filetype to set
    */
   public void setFiletype(String filetype)
   {
      this.filetype = filetype;
   }

   /**
    * @return the filename
    */
   public String getFilename()
   {
      return filename;
   }

   /**
    * @param filename the filename to set
    */
   public void setFilename(String filename)
   {
      this.filename = filename;
   }

   /**
    * @return the base64
    */
   public String getBase64()
   {
      return base64;
   }

   /**
    * @param base64 the base64 to set
    */
   public void setBase64(String base64)
   {
      this.base64 = base64;
   }

}
