/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.WebApplicationException;

public class PlcDateParam {
	
  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  private Calendar date;

  public PlcDateParam(String in) throws WebApplicationException {
    try {
      date = Calendar.getInstance();
      date.setTime(format.parse(in));
    }
    catch (ParseException exception) {
      throw new WebApplicationException(400);
    }
  }
  public Date getDate() {
    return date.getTime();
  }
  public String format() {
    return format.format(date.getTime());
  }
}