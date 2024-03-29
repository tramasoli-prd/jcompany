/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.powerlogic.jcompany.commons.util.interpolator;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A resource bundle locator, that loads resource bundles by simply
 * invoking <code>ResourceBundle.loadBundle(...)</code>.
 *
 * @author Hardy Ferentschik
 * @author Gunnar Morling
 */
public class PlatformResourceBundleLocator implements IResourceBundleLocator {

	private String bundleName;

	public PlatformResourceBundleLocator(String bundleName) {
		this.bundleName = bundleName;
	}

	/**
	 * Search current thread classloader for the resource bundle. If not found,
	 * search validator (this) classloader.
	 *
	 * @param locale The locale of the bundle to load.
	 *
	 * @return the resource bundle or <code>null</code> if none is found.
	 */
	public ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle rb = null;
		ClassLoader classLoader = PlatformResourceBundleLocator.class.getClassLoader();

		rb = loadBundle(classLoader, locale, bundleName+ " not found by validator classloader");
		return rb;
	}

	private ResourceBundle loadBundle(ClassLoader classLoader, Locale locale, String message) {
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(
					bundleName, locale,
					classLoader
					);
		}
		catch ( MissingResourceException e ) {
			
		}
		return rb;
	}
}
