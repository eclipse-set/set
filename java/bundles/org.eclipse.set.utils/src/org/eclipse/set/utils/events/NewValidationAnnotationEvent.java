/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.events;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.set.core.services.validation.AnnotationResult;

/**
 * A new validation annotation model is available.
 * 
 * @author Schaefer
 */
public class NewValidationAnnotationEvent
		implements ToolboxEvent, AnnotationResult {

	private static final String TOPIC = "toolboxevents/validation/annotation/new"; //$NON-NLS-1$

	private final IAnnotationModel model;
	private final IDocument document;

	/**
	 * Creates a event without a model or document.
	 */
	public NewValidationAnnotationEvent() {
		model = null;
		document = null;
	}

	/**
	 * @param model
	 *            the new validation annotation model
	 * @param document
	 *            the connected document
	 */
	public NewValidationAnnotationEvent(final IAnnotationModel model,
			final IDocument document) {
		this.model = model;
		this.document = document;
	}

	@Override
	public String getTopic() {
		return TOPIC;
	}

	@Override
	public IAnnotationModel getAnnotationModel() {
		return model;
	}

	@Override
	public IDocument getDocument() {
		return document;
	}
}
