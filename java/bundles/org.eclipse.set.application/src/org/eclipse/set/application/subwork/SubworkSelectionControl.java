/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.subwork;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.set.application.Messages;
import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.planningaccess.PlanningAccessService;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selects the subwork type for title box generation.
 * 
 * @author Brombacher
 */
public class SubworkSelectionControl {

	static final Logger LOGGER = LoggerFactory
			.getLogger(SubworkSelectionControl.class);

	private final MApplication application;

	private final IEventBroker broker;

	@Translation
	private final Messages messages;

	private IModelSession oldSession;

	ToolboxPartService partService;

	private ComboViewer comboViewer;

	PlanningAccessService planningAccessService;

	/**
	 * @param parent
	 *            the parent
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public SubworkSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		application = serviceProvider.application;
		broker = serviceProvider.broker;
		messages = serviceProvider.messages;
		partService = serviceProvider.partService;
		planningAccessService = serviceProvider.planningAccessService;
		createTableCombo(parent);
	}

	private void createTableCombo(final Composite parent) {
		comboViewer = new ComboViewer(parent);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		initCombo();
		comboViewer.addSelectionChangedListener(event -> {
			if (event
					.getSelection() instanceof final IStructuredSelection selection) {
				final Object first = selection.getFirstElement();
				if (first instanceof final String selectedString) {
					final ENUMUntergewerkArt selectedEnum = ENUMUntergewerkArt
							.get(selectedString);
					planningAccessService
							.setCurrentUntergewerkArt(selectedEnum);
				}
			}
		});

		setCombo(getSession());

		// register for session changes
		application.getContext().runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(final IEclipseContext context) {
				setCombo(context.get(IModelSession.class));
				return true;
			}
		});

	}

	private IModelSession getSession() {
		return application.getContext().get(IModelSession.class);
	}

	// correct size for combo
	private void initCombo() {
		clearCombo();
		comboViewer.add(messages.TableTypeSelectionControl_noSession);
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(false);
	}

	private List<String> getSubworkTypes(final IModelSession session) {
		if (session == null) {
			return java.util.List.of();
		}

		final var schnitt = session.getPlanProSchnittstelle();
		if (schnitt == null) {
			return java.util.List.of();
		}

		final var lst = schnitt.getLSTPlanung();
		if (lst == null) {
			return java.util.List.of();
		}

		final var fachdatenContainer = lst.getFachdaten();
		if (fachdatenContainer == null) {
			return java.util.List.of();
		}

		final var fachdaten = fachdatenContainer.getAusgabeFachdaten();
		if (fachdaten == null) {
			return java.util.List.of();
		}

		final java.util.Set<String> subtypes = new java.util.HashSet<>();

		for (final var fd : fachdaten) {
			final var uga = fd.getUntergewerkArt();
			if (uga == null) {
				continue;
			}
			final String type = String.valueOf(uga.getWert());
			subtypes.add(type);
		}

		final java.util.List<String> result = new java.util.ArrayList<>(
				subtypes);
		return result;
	}

	private void setCombo(final IModelSession session) {
		if (session == oldSession) {
			return;
		}
		oldSession = session;
		if (session == null) {
			initCombo();
		} else {
			clearCombo();
			final List<String> subworkTypes = getSubworkTypes(session);
			final List<String> sortedSubworkTypes = sortSubworkTypes(
					subworkTypes);
			comboViewer.setInput(sortedSubworkTypes.toArray());
			comboViewer.getCombo().select(sortedSubworkTypes.size() - 1);
			comboViewer.getCombo().setEnabled(true);
		}
	}

	private static List<String> sortSubworkTypes(
			final List<String> subworkTypes) {
		final List<String> sorted = new ArrayList<>();
		final String[] ORDER = {
				ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ATO.getLiteral(),
				ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ETCS.getLiteral(),
				ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ESTW.getLiteral(),
				ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_GEO.getLiteral() };

		for (final String key : ORDER) {
			if (subworkTypes.contains(key)) {
				sorted.add(key);
			}
		}
		return sorted;
	}

	private void clearCombo() {
		comboViewer.getCombo().removeAll();
		comboViewer.getCombo().clearSelection();
	}
}
