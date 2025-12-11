/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.subwork;

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.set.application.Messages;
import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.Events;
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

	@Translation
	private final Messages messages;

	private IModelSession oldSession;

	ToolboxPartService partService;

	private ComboViewer comboViewer;

	PlanningAccessService planningAccessService;

	private final IEventBroker broker;

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
					broker.send(Events.SUBWORK_CHANGED, null);
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

	private static List<String> getSubworkTypes(final IModelSession session) {

		final var projectListOpt = getNullableObject(session,
				s -> s.getPlanProSchnittstelle()
						.getLSTPlanung()
						.getObjektmanagement()
						.getLSTPlanungProjekt());

		if (projectListOpt.isEmpty()) {
			return List.of();
		}

		final var projects = projectListOpt.get();
		final Set<String> subworkTypes = new HashSet<>();

		for (final var project : projects) {

			final var projectGroupsOpt = getNullableObject(project,
					p -> p.getLSTPlanungGruppe()).orElse(new BasicEList<>());

			for (final var group : projectGroupsOpt) {

				final var subworkTypeOpt = getNullableObject(group,
						g -> g.getPlanungGAllg().getUntergewerkArt().getWert())
								.orElse(null);

				if (subworkTypeOpt != null) {
					subworkTypes.add(subworkTypeOpt.getLiteral());
				}
			}
		}

		return new ArrayList<>(subworkTypes);
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
			comboViewer.getCombo().select(0);
			comboViewer.getCombo().setEnabled(true);
			planningAccessService.setCurrentUntergewerkArt(
					ENUMUntergewerkArt.get(sortedSubworkTypes.getFirst()));
		}
	}

	private static final List<String> SUBWORK_TYPE_ORDER = Arrays.asList(
			ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ATO.getLiteral(),
			ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ETCS.getLiteral(),
			ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_ESTW.getLiteral(),
			ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_GEO.getLiteral());

	private static List<String> sortSubworkTypes(
			final List<String> subworkTypes) {
		final List<String> sortedSubworkTypes = new ArrayList<>(subworkTypes);
		sortedSubworkTypes.sort((a, b) -> {
			final int indexOfA = SUBWORK_TYPE_ORDER.indexOf(a);
			final int indexOfB = SUBWORK_TYPE_ORDER.indexOf(b);
			if (indexOfA == -1 && indexOfB == -1) {
				return a.compareTo(b);
			}
			if (indexOfA == -1) {
				return 1;
			}
			if (indexOfB == -1) {
				return -1;
			}

			final int indexCompare = Integer.compare(indexOfA, indexOfB);
			if (indexCompare != 0) {
				return indexCompare;
			}
			return a.compareTo(b);
		});

		return sortedSubworkTypes.isEmpty() ? List.of(
				ENUMUntergewerkArt.ENUM_UNTERGEWERK_ART_SONSTIGE.getLiteral())
				: sortedSubworkTypes;
	}

	private void clearCombo() {
		comboViewer.getCombo().removeAll();
		comboViewer.getCombo().clearSelection();
	}
}
