/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.set.basis.extensions.IDocumentExtensions;
import org.eclipse.set.basis.extensions.TagExtensions;
import org.eclipse.set.basis.text.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides PlanPro folding structure.
 * 
 * @author Schaefer
 */
public class PlanProFoldingStructureProvider {

	private static final Annotation[] EMPTY = new Annotation[0];

	static final Logger LOGGER = LoggerFactory
			.getLogger(PlanProFoldingStructureProvider.class);

	private static Map<Annotation, Position> getAdditions(
			final Set<Position> positions) {
		final HashMap<Annotation, Position> result = new HashMap<>();
		positions.forEach(
				position -> result.put(new ProjectionAnnotation(), position));
		return result;
	}

	private static Annotation[] getDeletions(
			final ProjectionAnnotationModel model,
			final Set<Position> positions) {
		final List<Annotation> deletions = new ArrayList<>();
		for (final Iterator<Annotation> iter = model
				.getAnnotationIterator(); iter.hasNext();) {
			final Annotation annotation = iter.next();
			if (annotation instanceof ProjectionAnnotation) {
				final Position position = model.getPosition(annotation);
				if (!positions.remove(position)) {
					deletions.add(annotation);
				}
			}
		}
		return deletions.toArray(new Annotation[deletions.size()]);
	}

	private final UISynchronize sync;

	private final ProjectionViewer viewer;

	IDocument document;

	/**
	 * @param viewer
	 *            the projection viewer
	 * @param sync
	 *            UI synchronize
	 */
	public PlanProFoldingStructureProvider(final ProjectionViewer viewer,
			final UISynchronize sync) {
		this.viewer = viewer;
		this.sync = sync;
	}

	/**
	 * @param document
	 *            the document
	 */
	public void setDocument(final IDocument document) {
		this.document = document;
	}

	/**
	 * Update folding regions with in given range.
	 * 
	 * @param range
	 *            the range
	 */
	public void updateFoldingRegions(final IRegion range) {
		final ProjectionAnnotationModel model = viewer
				.getProjectionAnnotationModel();
		if (model != null) {

			try {
				updateFoldingRegions(model, range);
			} catch (final BadLocationException e) {
				throw new RuntimeException(e);
			}

		}
	}

	private Set<Position> getFoldingPositions(final IRegion range)
			throws BadLocationException {
		final ArrayList<Tag> tags = IDocumentExtensions.getTags(document,
				range);
		LOGGER.debug("Found " + tags.size() + " tags."); //$NON-NLS-1$ //$NON-NLS-2$
		final Set<Position> positions = TagExtensions.getFoldingPositions(tags);
		return positions;
	}

	void updateFoldingRegions(final ProjectionAnnotationModel model,
			final IRegion range) throws BadLocationException {
		LOGGER.debug("Start updateFoldingRegions..."); //$NON-NLS-1$
		final Set<Position> positions = getFoldingPositions(range);
		LOGGER.debug("Found " + positions.size() + " positions."); //$NON-NLS-1$ //$NON-NLS-2$
		final Annotation[] deletions = getDeletions(model, positions);
		final Map<Annotation, Position> additions = getAdditions(positions);
		if (deletions.length != 0 || !additions.isEmpty()) {
			// IMPROVE Workaround for
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=406663
			sync.asyncExec(
					() -> model.modifyAnnotations(deletions, additions, EMPTY));
		}
	}

}
