/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.report;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * A validation annotation that can draw itself.
 * 
 * @author Schaefer
 */
public class ValidationAnnotation extends Annotation
		implements IAnnotationPresentation {

	private final Image image;

	/**
	 * Create a validation annotation.
	 * 
	 * @param image
	 *            the image used for the annotation
	 * 
	 */
	public ValidationAnnotation(final Image image) {
		super(false);
		this.image = image;
	}

	@Override
	public int getLayer() {
		return IAnnotationPresentation.DEFAULT_LAYER;
	}

	/**
	 * Draw this annotation.
	 * 
	 * @param gc
	 *            the drawing GC
	 * @param canvas
	 *            the canvas to draw on
	 * @param bounds
	 *            the bounds inside the canvas to draw on
	 */
	@Override
	public void paint(final GC gc, final Canvas canvas,
			final Rectangle bounds) {
		ImageUtilities.drawImage(image, gc, canvas, bounds, SWT.CENTER,
				SWT.CENTER);
	}
}
