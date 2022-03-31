/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import java.util.List;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BlockTextSelection;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.set.core.services.validation.AnnotationResult;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link LineTextViewer} implementation with {@link SourceViewer}.
 * 
 * @author Schaefer
 */
public class SourceTextLineViewer extends AbstractTextViewer {

	private static final int ANNOTATION_RULER_WIDTH = 16;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SourceTextLineViewer.class);
	private static final int OVERVIEW_RULER_WIDTH = 16;
	private static final String TEXT_FONT_DEFAULT_DESCRIPTION = "Courier New,10,normal"; //$NON-NLS-1$
	private static final String VALIDATION_PROBLEM = "Validation Problem"; //$NON-NLS-1$

	private static ISharedTextColors getSharedColors() {
		return EditorsPlugin.getDefault().getSharedTextColors();
	}

	private DefaultMarkerAnnotationAccess annotationAccess;

	private AnnotationRulerColumn annotationRulerColumn;
	private OverviewRuler overviewRuler;
	private CompositeRuler ruler;
	private final UISynchronize sync;
	private ProjectionViewer viewer;

	/**
	 * @param sync
	 *            UI synchronize
	 */
	public SourceTextLineViewer(final UISynchronize sync) {
		this.sync = sync;
	}

	/**
	 * @return the source viewer
	 */
	public SourceViewer getSourceViewer() {
		return viewer;
	}

	/**
	 * @param parent
	 *            the parent composite
	 * @param annotationModel
	 *            the annotation model
	 * @param annotationType
	 *            the annotation type to make visible within the viewer
	 * @param document
	 *            the document
	 */
	public void init(final Composite parent,
			final IAnnotationModel annotationModel, final String annotationType,
			final Document document) {
		configuration(parent);
		setAnnotationModel(document, annotationModel, annotationType);
		initProjectionSupport();
	}

	@Override
	public void init(final Composite parent, final List<String> lines) {
		configuration(parent);
		final Document document = new Document();
		document.set(getText(lines));
		final AnnotationModel annotationModel = new AnnotationModel();
		setAnnotationModel(document, annotationModel, ""); //$NON-NLS-1$
		initProjectionSupport();
	}

	@Override
	public void selectLine(final int lineNumber) {
		try {
			final IDocument document = getSourceViewer().getDocument();
			final IRegion lineInformation = document
					.getLineInformation(lineNumber - 1);
			final ISelection selection = new BlockTextSelection(document,
					lineNumber - 1, 0, lineNumber - 1,
					lineInformation.getLength(), 0);
			getSourceViewer().setSelection(selection, true);
		} catch (final BadLocationException e) {
			LOGGER.warn(e.getMessage());
		}
	}

	/**
	 * @param annotationResult
	 *            the new annotation result
	 */
	public void update(final AnnotationResult annotationResult) {
		final SourceViewer sourceViewer = getSourceViewer();
		final IDocument document = sourceViewer.getDocument();
		final int topIndex = sourceViewer.getTopIndex();
		setAnnotationModel(annotationResult, VALIDATION_PROBLEM);
		if (topIndex < document.getNumberOfLines()) {
			sourceViewer.setTopIndex(topIndex);
		}
	}

	@Override
	public void update(final List<String> lines) {
		final String text = getText(lines);
		final SourceViewer sourceViewer = getSourceViewer();
		final IDocument document = sourceViewer.getDocument();
		final int topIndex = sourceViewer.getTopIndex();
		document.set(text);
		if (topIndex < document.getNumberOfLines()) {
			sourceViewer.setTopIndex(topIndex);
		}
	}

	private void configuration(final Composite parent) {
		// ruler
		ruler = new CompositeRuler();

		// description to resource transformation
		final LocalResourceManager resourceManager = new LocalResourceManager(
				JFaceResources.getResources(), parent);
		final DescriptionToResourceTransformation transformation = new DescriptionToResourceTransformation(
				resourceManager);

		// viewer configuration
		annotationAccess = new DefaultMarkerAnnotationAccess();
		overviewRuler = new OverviewRuler(annotationAccess,
				OVERVIEW_RULER_WIDTH, getSharedColors());
		viewer = new ProjectionViewer(parent, ruler, overviewRuler, true,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER
						| SWT.FULL_SELECTION);
		viewer.setEditable(false);
		final ToolboxViewerConfiguration configuration = new ToolboxViewerConfiguration(
				parent, transformation, sync);
		viewer.configure(configuration);

		// annotations
		annotationRulerColumn = new AnnotationRulerColumn(
				ANNOTATION_RULER_WIDTH, annotationAccess);
		viewer.addVerticalRulerColumn(annotationRulerColumn);

		// line numbers
		final LineNumberRulerColumn lineNumberRulerColumn = new LineNumberRulerColumn();
		lineNumberRulerColumn.setForeground(
				Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
		viewer.addVerticalRulerColumn(lineNumberRulerColumn);

		// text widget font
		final String fontDescription = ToolboxConfiguration
				.getTextFont(TEXT_FONT_DEFAULT_DESCRIPTION);
		final Font font = transformation.transformToFont(fontDescription);
		if (font != null) {
			// RAP-IMP Styled text is not implemented in RAP -> dummy in
			// rap.supplement
			viewer.getTextWidget().setFont(font);
		}
	}

	private void initProjectionSupport() {
		// projection support
		final ProjectionSupport projectionSupport = new ProjectionSupport(
				viewer, annotationAccess, getSharedColors());
		projectionSupport.install();
		viewer.doOperation(ProjectionViewer.TOGGLE);
		LOGGER.debug("folded."); //$NON-NLS-1$
	}

	private void setAnnotationModel(final AnnotationResult annotationResult,
			final String validationProblem) {
		setAnnotationModel(annotationResult.getDocument(),
				annotationResult.getAnnotationModel(), validationProblem);
	}

	private void setAnnotationModel(final IDocument document,
			final IAnnotationModel annotationModel,
			final String annotationType) {
		viewer.setDocument(document, annotationModel);
		ruler.setModel(annotationModel);
		annotationRulerColumn.addAnnotationType(annotationType);
		annotationRulerColumn.setHover(new DefaultAnnotationHover());
		viewer.setDocument(viewer.getDocument(), annotationModel);
		viewer.setHoverControlCreator(DefaultInformationControl::new);
		viewer.setAnnotationHover(new DefaultAnnotationHover());
		viewer.showAnnotations(true);
		overviewRuler.addHeaderAnnotationType(annotationType);
	}
}
