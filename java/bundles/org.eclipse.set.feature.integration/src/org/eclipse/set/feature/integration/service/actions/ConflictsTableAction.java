/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration.service.actions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.merge.MergeService.ContextProvider;
import org.eclipse.set.feature.integration.Messages;
import org.eclipse.set.feature.integration.SessionToIntegrationViewTransformation;
import org.eclipse.set.model.integrationview.Conflict;
import org.eclipse.set.model.simplemerge.Resolution;
import org.eclipse.set.model.simplemerge.SMatch;
import org.eclipse.set.model.temporaryintegration.TemporaryIntegration;
import org.eclipse.set.model.temporaryintegration.extensions.TemporaryIntegrationExtensions;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;

/**
 * Edit conflicts of the conflict table.
 * 
 * @author Schaefer
 */
public class ConflictsTableAction implements Consumer<ColumnViewer> {

	private abstract class AbstractTableAction extends Action {

		public AbstractTableAction(final String text) {
			super(text);
			setEnabled(!tableViewer.getSelection().isEmpty());
			tableViewer.addSelectionChangedListener(
					e -> setEnabled(!e.getSelection().isEmpty()));
		}
	}

	private class ResolveSelectedAction extends AbstractTableAction {
		private final Resolution resolution;

		public ResolveSelectedAction(final String text,
				final Resolution resolution) {
			super(text);
			this.resolution = resolution;
		}

		@Override
		public void run() {
			final TemporaryIntegration temporaryIntegration = session
					.getTemporaryIntegration().get();
			final StructuredSelection selection = (StructuredSelection) tableViewer
					.getSelection();
			Arrays.stream(selection.toArray())
					.map(item -> transformation.getMatch((Conflict) item))
					.forEach(match -> TemporaryIntegrationExtensions
							.manualMerge(temporaryIntegration,
									session.getEditingDomain(), match,
									resolution, contextProvider));
			eventBroker.send(Events.MODEL_CHANGED,
					session.getPlanProSchnittstelle());
			transformation.transform(session, Optional.empty(),
					Optional.empty(), Optional.empty());
			updateButtonAction.get();
		}
	}

	private class ResolveSelectedPrimary extends ResolveSelectedAction {
		public ResolveSelectedPrimary() {
			super(messages.ConflictsTableAction_ResolvePrimary,
					Resolution.PRIMARY_MANUAL);
		}
	}

	private class ResolveSelectedSecondary extends ResolveSelectedAction {
		public ResolveSelectedSecondary() {
			super(messages.ConflictsTableAction_ResolveSecondary,
					Resolution.SECONDARY_MANUAL);
		}
	}

	private class ResolveUnresolvedAction extends AbstractTableAction {
		private final Resolution resolution;

		public ResolveUnresolvedAction(final String text,
				final Resolution resolution) {
			super(text);
			this.resolution = resolution;
		}

		@Override
		public void run() {
			final TemporaryIntegration temporaryIntegration = session
					.getTemporaryIntegration().get();
			final Map<ContainerType, List<SMatch>> selection = TemporaryIntegrationExtensions
					.getOpenConflictMatches(temporaryIntegration);
			selection.entrySet().stream().map(entry -> entry.getValue())
					.flatMap(List::stream)
					.forEach(match -> TemporaryIntegrationExtensions
							.manualMerge(temporaryIntegration,
									session.getEditingDomain(), match,
									resolution, contextProvider));

			eventBroker.send(Events.MODEL_CHANGED,
					session.getPlanProSchnittstelle());
			transformation.transform(session, Optional.empty(),
					Optional.empty(), Optional.empty());
			updateButtonAction.get();
		}
	}

	private class ResolveUnresolvedPrimary extends ResolveUnresolvedAction {
		public ResolveUnresolvedPrimary() {
			super(messages.ConflictsTableAction_ResolveUnresolvedPrimary,
					Resolution.PRIMARY_MANUAL);
		}
	}

	private class ResolveUnresolvedSecondary extends ResolveUnresolvedAction {
		public ResolveUnresolvedSecondary() {
			super(messages.ConflictsTableAction_ResolveUnresolvedSecondary,
					Resolution.SECONDARY_MANUAL);
		}
	}

	private class UnresolveSelectedAction extends ResolveSelectedAction {
		public UnresolveSelectedAction() {
			super(messages.ConflictsTableAction_Unresolve,
					Resolution.PRIMARY_UNRESOLVED);
		}
	}

	final ContextProvider contextProvider;

	final Messages messages;
	final IModelSession session;
	ColumnViewer tableViewer;
	final SessionToIntegrationViewTransformation transformation;
	final Supplier<Void> updateButtonAction;
	private final IEventBroker eventBroker;

	/**
	 * @param session
	 *            the model session
	 * @param transformation
	 *            the session to merge view transformation
	 * @param messages
	 *            the translations
	 * @param contextProvider
	 *            the context provider
	 * @param updateButtonAction
	 *            the action for updating the view buttons
	 * @param eventBroker
	 *            the event broker
	 */
	public ConflictsTableAction(final IModelSession session,
			final SessionToIntegrationViewTransformation transformation,
			final ContextProvider contextProvider,
			final Supplier<Void> updateButtonAction, final Messages messages,
			final IEventBroker eventBroker) {
		this.session = session;
		this.transformation = transformation;
		this.messages = messages;
		this.contextProvider = contextProvider;
		this.updateButtonAction = updateButtonAction;
		this.eventBroker = eventBroker;
	}

	@Override
	public void accept(final ColumnViewer viewer) {
		tableViewer = viewer;

		final MenuManager menuManager = new MenuManager();

		// actions
		menuManager.add(new ResolveSelectedPrimary());
		menuManager.add(new ResolveSelectedSecondary());
		menuManager.add(new Separator());
		menuManager.add(new ResolveUnresolvedPrimary());
		menuManager.add(new ResolveUnresolvedSecondary());
		menuManager.add(new Separator());
		menuManager.add(new UnresolveSelectedAction());

		final Table table = (Table) viewer.getControl();
		final Menu menu = menuManager.createContextMenu(table);
		table.setMenu(menu);
	}

	/**
	 * @return the table viewer for this action
	 */
	public ColumnViewer getViewer() {
		return tableViewer;
	}
}
