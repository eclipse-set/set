/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.validation;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;

/**
 * Combination of annotation model and connected document.
 * 
 * @author Schaefer
 */
public interface AnnotationResult {

	/**
	 * @return the annotation model
	 */
	IAnnotationModel getAnnotationModel();

	/**
	 * @return the connected document
	 */
	IDocument getDocument();
}
