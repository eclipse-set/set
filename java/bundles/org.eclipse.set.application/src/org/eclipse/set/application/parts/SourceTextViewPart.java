/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.parts;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.validation.ValidationAnnotationService;
import org.eclipse.set.utils.events.EventRegistration;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.NewValidationAnnotationEvent;
import org.eclipse.set.utils.text.SourceTextLineViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Displays PlanPro model as source text.
 * 
 * @author Schaefer
 */
public class SourceTextViewPart extends AbstractTextViewPart<IModelSession> {

	@Inject
	private UISynchronize sync;

	@Inject
	private ValidationAnnotationService validationAnnotationService;

	private SourceTextLineViewer viewer;
	private EventRegistration eventRegistration;

	/**
	 * Create View.
	 */
	public SourceTextViewPart() {
		super(IModelSession.class, true);
	}

	@Override
	protected void createView(final Composite parent) {
		super.createView(parent);

		eventRegistration = new EventRegistration(getBroker());
		eventRegistration.registerHandler(JumpToSourceLineEvent.class,
				this::handleJumpToSourceLineEvent);
		eventRegistration.registerHandler(NewValidationAnnotationEvent.class,
				this::handle);

		validationAnnotationService.getResult().ifPresent(getViewer()::update);
	}

	private void handleJumpToSourceLineEvent(
			final JumpToSourceLineEvent event) {
		getViewer().selectLine(event.getLineNumber());
	}

	private void handle(final NewValidationAnnotationEvent event) {
		getViewer().update(event);
	}

	@PreDestroy
	private void preDestroy() {
		eventRegistration.unsubscribeAll();
	}

	@Override
	protected SourceTextLineViewer getViewer() {
		if (viewer == null) {
			viewer = new SourceTextLineViewer(sync);
		}
		return viewer;
	}
}
