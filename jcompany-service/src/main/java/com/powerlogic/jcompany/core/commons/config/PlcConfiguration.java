/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.commons.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

/**
 * 
 * Utilitário para carregaento e manipulação de chaves e valores pré-definidos no arquito "configuration.properties".
 * 
 * É utilizado no carregamento das classes de domínio discreto (Enums), 
 * 
 * As classes a serem carregadas ficam definidas o padrão:
 * 
 * 	- EX.: sexo=com.powerlogic.jcompany.rhdemo.app.model.domain.Sexo
 * 
 * @category Configurator
 * @since 1.0.0
 * @author Powerlogic
 *
 */
public class PlcConfiguration {

	private static final PlcConfiguration INSTANCE = new PlcConfiguration();

	private static final String CONFIG_PROPERTIES = "META-INF/configuration.properties";

	private Properties config;

	/**
	 * Recupera a instancia corrente.
	 * 
	 * @return instance 
	 */
	public static PlcConfiguration get() {
		return INSTANCE;
	}

	/**
	 * Executado sempre após contruir o objeto.
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			config = loadConfiguration();
		} catch (Exception e) {
			throw new IllegalStateException("Error in load config", e);
		}
	}

	protected Properties getConfig() {
		if (config == null) {
			init();
		}
		return config;
	}

	/**
	 * Recupera uma chave do Mapa.
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return get(key, null);
	}

	/**
	 * Recupera uma chave e um valor padrão, caso não encontre a chave.
	 * 
	 * @param key
	 * @param def
	 * @return
	 */
	public String get(String key, String def) {
		return getConfig().getProperty(key, def);
	}

	/**
	 * Recupera o valor convertido em Integer.
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getInt(String key, int def) {
		String val = getConfig().getProperty(key);
		return val == null ? def : Integer.parseInt(val);
	}

	/**
	 * 
	 * Carrega o arquivo de configuração e insere as chaves no mapa.
	 * 
	 * @return properties - Mapa de propriedades
	 * @throws IOException - File not Found
	 */
	private synchronized Properties loadConfiguration() throws IOException {

		InputStream configuration = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(CONFIG_PROPERTIES);

		if (configuration == null) {
			throw new IllegalStateException("Database configuration not found");
		}
		try {
			Properties properties = new Properties();
			properties.load(configuration);
			return properties;
		} finally {
			configuration.close();
		}
	}
}
