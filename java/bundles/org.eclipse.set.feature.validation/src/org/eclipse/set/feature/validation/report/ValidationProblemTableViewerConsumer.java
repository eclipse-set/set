/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.report;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ToolboxEvents;

/**
 * Links the validation problem table to the event bus and the part service
 * 
 * @param <S>
 *            the session type
 * 
 * @author Schaefer / Peters
 */
public class ValidationProblemTableViewerConsumer<S extends IModelSession>
		implements ISelectionChangedListener, Consumer<ColumnViewer>,
		IDoubleClickListener {
	private final static String SOURCE_TEXT_VIEWER_PART_ID = "org.eclipse.set.application.descriptions.SourceTextViewDescriptionService"; //$NON-NLS-1$
	private final IEventBroker broker;
	private final BasePart<S> source;
	private final ToolboxPartService toolboxPartService;
	private ValidationProblem lastSelectedProblem;

	/**
	 * @param broker
	 *            the event broker
	 * @param source
	 *            the source
	 * @param toolboxPartService
	 *            the toolbox part service
	 */
	public ValidationProblemTableViewerConsumer(final IEventBroker broker,
			final BasePart<S> source,
			final ToolboxPartService toolboxPartService) {
		this.broker = broker;
		this.source = source;
		this.toolboxPartService = toolboxPartService;
	}

	@Override
	public void accept(final ColumnViewer viewer) {
		viewer.addSelectionChangedListener(this);
		viewer.addDoubleClickListener(this);
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		if (!(selection instanceof StructuredSelection)) {
			return;
		}
		final StructuredSelection structuredSelection = (StructuredSelection) selection;
		final List<?> selectionList = structuredSelection.toList();
		if (selectionList.size() != 1
				|| !(selectionList.get(0) instanceof ValidationProblem)) {
			return;
		}
		lastSelectedProblem = (ValidationProblem) selectionList.get(0);
		final int lineNumber = lastSelectedProblem.getLineNumber();
		ToolboxEvents.send(broker,
				new JumpToSourceLineEvent(lineNumber, source));
	}

	@Override
	public void doubleClick(final DoubleClickEvent event) {
		toolboxPartService.showPart(SOURCE_TEXT_VIEWER_PART_ID);

		// If we have a selected problem, select it after showing the part
		if (lastSelectedProblem != null) {
			ToolboxEvents.send(broker, new JumpToSourceLineEvent(
					lastSelectedProblem.getLineNumber(), source));
		}
	}
}
