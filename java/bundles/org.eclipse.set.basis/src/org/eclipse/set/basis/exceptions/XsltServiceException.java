/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import java.util.Map;

import javax.xml.transform.TransformerException;

/**
 * An exception while executing the XSLT service.
 * 
 * @author Schaefer
 */
public class XsltServiceException extends RuntimeException {

	/**
	 * Provider for expanded message.
	 */
	public static interface ExpandedMessageProvider {
		/**
		 * @param e
		 *            the transformer exception
		 * 
		 * @return the expanded message
		 */
		String getExpandedMessage(final XsltServiceException e);
	}

	private static String createMessage(final TransformerException e,
			final String transformation, final String source,
			final Map<String, String> parameters) {
		return String.format(
				"exception=%s message=%s transformation=%s source=%s parameters=%s", //$NON-NLS-1$
				e.getClass().getSimpleName(), e.getMessage(), transformation,
				source, parameters);
	}

	private final Map<String, String> parameters;
	private final ExpandedMessageProvider provider;
	private final String source;
	private final String transformation;

	/**
	 * @param e
	 *            the transformer exception
	 * @param transformation
	 *            the transformation
	 * @param source
	 *            the source
	 * @param parameters
	 *            the parameters
	 * @param provider
	 *            the expanded message provider
	 */
	public XsltServiceException(final TransformerException e,
			final String transformation, final String source,
			final Map<String, String> parameters,
			final ExpandedMessageProvider provider) {
		super(createMessage(e, transformation, source, parameters), e);
		this.transformation = transformation;
		this.source = source;
		this.parameters = parameters;
		this.provider = provider;
	}

	/**
	 * @return the expanded message
	 */
	public String getExpandedMessage() {
		return provider.getExpandedMessage(this);
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @return the transformation
	 */
	public String getTransformation() {
		return transformation;
	}
}
