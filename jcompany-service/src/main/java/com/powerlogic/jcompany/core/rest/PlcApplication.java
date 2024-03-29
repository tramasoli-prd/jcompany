/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlcApplication extends Application {
	
	private static final Logger log = LoggerFactory.getLogger(PlcApplication.class);

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		Class clazz;
		try {
			clazz = Class.forName("org.glassfish.jersey.jackson.JacksonFeature");
			classes.add(clazz);
		} catch (ClassNotFoundException e) {
			log.info("JAX-RS API - Jersey não detectado pela aplicação. Utilizando o Resteasy como implementação REST. ");
		}
		return classes;
	}
	
}
