/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.services.xslt;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

/**
 * Diese Schnittstelle beschreibt die Fähigkeiten des XSLT-Dienstes.
 * 
 * @author Schaefer
 */
public interface XsltService {

	/**
	 * Transform the source with the transformation.
	 * 
	 * @param <T>
	 *            type of the class context
	 * @param classContext
	 *            the class context (used to locate resources)
	 * @param resourceBase
	 *            resource base relative to the bundle of the class context
	 *            (used to locate resources)
	 * @param transformation
	 *            the transformation
	 * @param source
	 *            the source
	 * 
	 * @return the result of the transformation
	 */
	<T> String transform(Class<T> classContext, String resourceBase,
			String transformation, Path source);

	/**
	 * Transform the source with the transformation.
	 * 
	 * @param <T>
	 *            type of the class context
	 * @param classContext
	 *            the class context (used to locate resources)
	 * @param resourceBase
	 *            resource base relative to the bundle of the class context
	 *            (used to locate resources)
	 * @param transformation
	 *            the transformation
	 * @param source
	 *            the source
	 * @param parameters
	 *            transformation parameters
	 * 
	 * @return the result of the transformation
	 */
	<T> String transform(Class<T> classContext, String resourceBase,
			String transformation, Path source, Map<String, String> parameters);

	/**
	 * Transformiert die Quelle durch die Transformation.
	 * 
	 * @param transformation
	 *            die Transformation
	 * @param source
	 *            die Quelle
	 * 
	 * @return das Ergebnis der Transformation
	 */
	String transform(InputStream transformation, InputStream source);

	/**
	 * Transformiert die Quelle durch die Transformation.
	 * 
	 * @param transformation
	 *            die Transformation
	 * @param source
	 *            die Quelle
	 * 
	 * @return das Ergebnis der Transformation
	 */
	String transform(InputStream transformation, Path source);

	/**
	 * Transformiert die Quelle durch die Transformation.
	 * 
	 * @param transformation
	 *            the transformation
	 * @param source
	 *            the source
	 * @param parameters
	 *            transformation parameters
	 * 
	 * @return das Ergebnis der Transformation
	 */
	String transform(InputStream transformation, Path source,
			Map<String, String> parameters);

	/**
	 * Transformiert die Quelle durch die Transformation.
	 * 
	 * @param transformation
	 *            die Transformation
	 * @param source
	 *            die Quelle
	 * 
	 * @return das Ergebnis der Transformation
	 */
	String transform(Path transformation, Path source);
}
