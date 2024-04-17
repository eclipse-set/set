/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.placearea;

import static org.eclipse.set.ppmodel.extensions.StellBereichExtensions.getStellBeteichBezeichnung;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.set.application.Messages;
import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.SelectionPlaceArea;
import org.eclipse.set.utils.events.SelectionPlaceArea.PlaceAreaValue;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class PlaceAreaSelectionControl {

	static final Logger LOGGER = LoggerFactory
			.getLogger(PlaceAreaSelectionControl.class);

	private static class PlaceAreaLabelProvider extends LabelProvider {
		public PlaceAreaLabelProvider() {
			super();
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final PlaceAreaValue value) {
				return value.areaName();
			}
			return super.getText(element);
		}

	}

	private final MApplication application;

	private final IEventBroker broker;

	private ComboViewer comboViewer;

	@Translation
	private final Messages messages;

	ToolboxPartService partService;

	MultiContainer_AttributeGroup container;

	private ToolboxEventHandler<NewTableTypeEvent> newTableTypeHandler;
	private TableType tableType;
	IModelSession oldSession;

	/**
	 * @param parent
	 *            the parent
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public PlaceAreaSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		application = serviceProvider.application;
		broker = serviceProvider.broker;
		messages = serviceProvider.messages;
		partService = serviceProvider.partService;
		createCombo(parent);
	}

	private void createCombo(final Composite parent) {
		comboViewer = new ComboViewer(parent);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new PlaceAreaLabelProvider());
		initCombo();
		comboViewer.addSelectionChangedListener(this::selectionPlaceArea);
		newTableTypeHandler = new DefaultToolboxEventHandler<>() {

			@Override
			public void accept(final NewTableTypeEvent t) {
				initCombo();
				tableType = t.getTableType();
				setCombo();
			}
		};
		ToolboxEvents.subscribe(broker, NewTableTypeEvent.class,
				newTableTypeHandler);

		// register for session changes
		application.getContext().runAndTrack(new RunAndTrack() {
			@Override
			public boolean changed(final IEclipseContext context) {
				if (getSession() != null) {
					final IModelSession session = context
							.get(IModelSession.class);
					if (session != oldSession) {
						oldSession = session;
					}
					if (session == null) {
						initCombo();
					} else {
						// Default table type
						tableType = TableType.DIFF;
						setCombo();
					}

				}
				return true;
			}
		});
	}

	private void setCombo() {
		comboViewer.getCombo().removeAll();
		final List<PlaceAreaValue> values = new LinkedList<>();
		switch (tableType) {
		case FINAL:
			values.addAll(getComboValues(getSession(), ContainerType.FINAL));
			break;
		case INITIAL:
			values.addAll(getComboValues(getSession(), ContainerType.INITIAL));
			break;
		case SINGLE:
			values.addAll(getComboValues(getSession(), ContainerType.SINGLE));
			break;
		case DIFF:
			values.addAll(getDiffComboValues());
			break;
		default:
			break;
		}
		if (values.isEmpty()) {
			comboViewer.add(messages.PlaceAreaCombo_Default_Value);
			comboViewer.getCombo().select(0);
			comboViewer.getCombo().setEnabled(false);
			return;
		}

		comboViewer.setInput(values);
		if (values.size() > 1) {
			comboViewer.insert(messages.PlaceAreaCombo_All, values.size());
		}
		comboViewer.insert(messages.PlaceAreaCombo_Default_Value, 0);

		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(true);
	}

	private List<PlaceAreaValue> getDiffComboValues() {
		final List<PlaceAreaValue> values = new LinkedList<>();
		final List<PlaceAreaValue> initialValues = getComboValues(getSession(),
				ContainerType.INITIAL);
		values.addAll(initialValues);
		final MultiContainer_AttributeGroup finalContainer = getSession()
				.getContainer(ContainerType.FINAL);
		if (finalContainer == null) {
			return values;
		}

		if (initialValues.isEmpty()) {
			return getComboValues(getSession(), ContainerType.FINAL);
		}

		for (final Stell_Bereich finalArea : finalContainer.getStellBereich()) {
			final String finalAreaId = finalArea.getIdentitaet().getWert();
			final Optional<PlaceAreaValue> initialArea = values.stream()
					.filter(area -> area.area().getIdentitaet().getWert()
							.equals(finalAreaId))
					.findFirst();

			if (initialArea.isEmpty()) {
				String areaName = getStellBeteichBezeichnung(finalArea);
				if (areaName == null) {
					areaName = String.format("Stellbereich %d", //$NON-NLS-1$
							Integer.valueOf(values.size()));
				}
				values.add(new PlaceAreaValue(areaName, finalArea,
						ContainerType.FINAL));
			}
		}
		return values;
	}

	/**
	 * Get combo value for the container. When the area in this container
	 * haven't designation, then describe the area with index number
	 * 
	 * @param selectedContainer
	 *            the multicontainer
	 * @param tableType
	 *            the table type
	 * @return
	 */
	private List<PlaceAreaValue> getComboValues(final IModelSession session,
			final ContainerType containerType) {
		final MultiContainer_AttributeGroup selectedContainer = session
				.getContainer(containerType);
		if (selectedContainer == null) {
			initCombo();
			return Collections.emptyList();
		}

		this.container = selectedContainer;
		if (selectedContainer.getStellBereich() == null
				|| !selectedContainer.getStellBereich().iterator().hasNext()) {
			comboViewer.getCombo().setEnabled(false);
			return Collections.emptyList();
		}
		final List<PlaceAreaValue> values = new LinkedList<>();
		int i = 1;
		for (final Stell_Bereich area : selectedContainer.getStellBereich()) {
			String areaName = getStellBeteichBezeichnung(area);
			if (areaName == null) {
				areaName = String.format("Stellbereich %d", //$NON-NLS-1$
						Integer.valueOf(i));
			}
			values.add(new PlaceAreaValue(areaName, area, containerType));
			i++;
		}
		return values;
	}

	private IModelSession getSession() {
		return application.getContext().get(IModelSession.class);
	}

	private void initCombo() {
		comboViewer.getCombo().removeAll();
		comboViewer
				.setInput(List.of(messages.TableTypeSelectionControl_noSession,
						messages.PlaceAreaCombo_Default_Value));
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(false);
	}

	@SuppressWarnings("unchecked")
	private void selectionPlaceArea(final SelectionChangedEvent e) {
		if (e.getSelection() instanceof final IStructuredSelection selection) {
			final Object selectedElement = selection.getFirstElement();
			if (selectedElement instanceof final PlaceAreaValue selected) {
				ToolboxEvents.send(broker,
						new SelectionPlaceArea(selected, tableType));
				return;
			}

			if (selectedElement instanceof final String msg) {
				if (msg.equals(messages.PlaceAreaCombo_Default_Value)) {
					ToolboxEvents.send(broker,
							new SelectionPlaceArea(tableType));
				} else if (msg.equals(messages.PlaceAreaCombo_All)) {
					try {
						final List<PlaceAreaValue> allValues = List.class
								.cast(comboViewer.getInput());
						ToolboxEvents.send(broker, new SelectionPlaceArea(
								Set.copyOf(allValues), tableType));
					} catch (final Exception exc) {
						throw new RuntimeException(exc);
					}
				}
			}
		}

	}
}
