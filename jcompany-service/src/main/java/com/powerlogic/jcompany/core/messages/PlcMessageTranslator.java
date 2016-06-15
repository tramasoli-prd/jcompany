/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import org.apache.commons.lang3.StringUtils;

import com.powerlogic.jcompany.core.util.ConstantUtil;

public abstract class PlcMessageTranslator {

	private static final String MESSAGE_ARG = "%s";
	
	private static UTF8Control utf8Control = new UTF8Control();

	private static class StringMessageKey implements IPlcMessageKey {
		private String name;

		public StringMessageKey(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		public static PlcMessageType getType(String messageKey) {
			PlcMessageType type = PlcMessageType.valueOrNull(StringUtils.substringBefore(messageKey, ConstantUtil.DOT));

			if (type == null) {
				type = PlcMessageType.INFO;
			}
			return type;
		}
	}

	private Map<String, PlcMessageMap> allMessages = new HashMap<String, PlcMessageMap>();

	private String[] resourceBundleNames;

	public PlcMessageTranslator() {
		
	}
	
	public PlcMessageTranslator(String... resourceBundleNames) {
		this.resourceBundleNames = resourceBundleNames;
	}

	public PlcMessageMap getAllMessages(Locale locale) {
		if (locale==null) {
			locale = Locale.getDefault();
		}
		PlcMessageMap messageMap = allMessages.get(locale.toLanguageTag());
		if (messageMap==null) {
			messageMap = new PlcMessageMap();
			for(String resourceBundleName : resourceBundleNames) {
				messageMap.addAll(getAllMessages(getBundle(resourceBundleName, locale)));
			}
			allMessages.put(locale.toLanguageTag(), messageMap);
		}
		return messageMap;
	}

	protected PlcMessageMap getAllMessages(ResourceBundle bundle) {
		PlcMessageMap messageMap = new PlcMessageMap();
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			messageMap.add(createMessage(bundle, keys.nextElement()));
		}
		return messageMap;
	}

	private PlcMessageEntry createMessage(ResourceBundle bundle, String messageKey) {

		String messageString = bundle.getString(messageKey);

		PlcMessageEntry messageEntry = new PlcMessageEntry(new StringMessageKey(messageKey),
				StringMessageKey.getType(messageString));

		messageEntry.setMessage(messageString);

		return messageEntry;
	}
	
	public PlcMessageMap translate(PlcMessageMap messageMap) {
		return translate(messageMap, Locale.getDefault());
	}

	public PlcMessageMap translate(PlcMessageMap messageMap, Locale locale) {

		for(int i = 0; i < resourceBundleNames.length; i++ ) {

			ResourceBundle bundle = getBundle(resourceBundleNames[i], locale);

			for (Entry<PlcMessageType, List<PlcMessageEntry>> entry : messageMap.getMessages().entrySet()) {
				translate(bundle, entry.getValue());
			}

		}
		return messageMap;
	}

	public PlcMessageEntry translate(PlcMessageEntry entry) {
		return translate(entry, Locale.getDefault());
	}
	
	public PlcMessageEntry translate(PlcMessageEntry entry, Locale locale) {
		
		for(int i = 0; i < resourceBundleNames.length; i++ ) {
			ResourceBundle bundle = getBundle(resourceBundleNames[i], locale);		
			translateMessage(bundle, entry);
		}
		return entry;
	}

	protected void translate(ResourceBundle bundle, List<PlcMessageEntry> value) {
		for (PlcMessageEntry message : value) {
			translateMessage(bundle, message);
		}
	}

	protected void translateMessage(ResourceBundle bundle, PlcMessageEntry message) {
		message.setMessage(getMessage(bundle, message));
	}

	protected String getMessage(ResourceBundle bundle, PlcMessageEntry message) {
		String messageString = getString(bundle, message.getType().lowerName() + "." + message.getKey().getName());

		if (StringUtils.isBlank(messageString)) {
			messageString = getString(bundle, message.getKey().getName());
		}
		if (StringUtils.isBlank(messageString)) {
			messageString = message.getKey().getName();
		}

		if (messageString.contains(MESSAGE_ARG)) {
			return String.format(messageString, (Object[]) message.getArgs());
		}
		return messageString;
	}

	private ResourceBundle getBundle(String resourceBundleName, Locale locale) {
		return ResourceBundle.getBundle(resourceBundleName, locale, utf8Control);
	}

	private String getString(ResourceBundle bundle, String key) {
		try {
			return bundle.getString(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 *  Esta classe é utilizada para carregar os arquivos de bundle com encoding UTF-8.
	 *  Fonte: http://stackoverflow.com/questions/4659929/how-to-use-utf-8-in-resource-properties-with-resourcebundle
	 * @author Olisses
	 */
	public static class UTF8Control extends Control {
		public ResourceBundle newBundle
		(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
				throws IllegalAccessException, InstantiationException, IOException
		{
			// The below is a copy of the default implementation.
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, "properties");
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					// Only this line is changed to make it to read properties files as UTF-8.
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
				} finally {
					stream.close();
				}
			}
			return bundle;
		}
	}
}
