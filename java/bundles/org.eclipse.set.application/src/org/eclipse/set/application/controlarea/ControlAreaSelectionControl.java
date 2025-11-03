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
package org.eclipse.set.application.controlarea;

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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.set.application.Messages;
import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent.ControlAreaValue;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Selection combo for the control area. The table will only show the objects,
 * which lie on this control area
 * 
 * @author Truong
 */
public class ControlAreaSelectionControl {

	static final Logger LOGGER = LoggerFactory
			.getLogger(ControlAreaSelectionControl.class);

	private static class ControlAreaLabelProvider extends LabelProvider {
		public ControlAreaLabelProvider() {
			super();
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final ControlAreaValue value) {
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

	private Object oldSelectionValue;

	/**
	 * @param parent
	 *            the parent
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public ControlAreaSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		application = serviceProvider.application;
		broker = serviceProvider.broker;
		messages = serviceProvider.messages;
		partService = serviceProvider.partService;
		createCombo(parent);
		// Reset combo value, when close session
		broker.subscribe(Events.CLOSE_SESSION, this::closeSession);
	}

	private void createCombo(final Composite parent) {
		comboViewer = new ComboViewer(parent);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new ControlAreaLabelProvider());
		initCombo();
		comboViewer.addSelectionChangedListener(this::selectionControlArea);
		newTableTypeHandler = new DefaultToolboxEventHandler<>() {

			@Override
			public void accept(final NewTableTypeEvent t) {
				initCombo();
				setCombo(t.getTableType());
				// Send update event, when table type change
				seletcionControlArea(comboViewer.getSelection(),
						t.getTableType());
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
					if (session == null) {
						initCombo();
					} else {
						// Default table type
						tableType = PlanProSchnittstelleExtensions
								.isPlanning(session.getPlanProSchnittstelle())
										? TableType.DIFF
										: TableType.SINGLE;
						setCombo(tableType);
					}
				}
				return true;
			}
		});
	}

	private void setCombo(final TableType type) {
		comboViewer.getCombo().removeAll();
		final List<ControlAreaValue> values = new LinkedList<>();
		switch (type) {
			case FINAL:
				values.addAll(
						getComboValues(getSession(), ContainerType.FINAL));
				break;
			case INITIAL:
				values.addAll(
						getComboValues(getSession(), ContainerType.INITIAL));
				break;
			case SINGLE:
				setSinglePlanControlAreaCombo();
				seletcionControlArea(comboViewer.getSelection(),
						TableType.SINGLE);
				return;
			case DIFF:
				values.addAll(getDiffComboValues());
				break;
			default:
				break;
		}
		if (values.isEmpty()) {
			setEmtpyControlAreaCombo();
			return;
		}

		comboViewer.setInput(values);
		if (values.size() > 1) {
			comboViewer.insert(messages.ControlAreaCombo_All_ControlArea,
					values.size());
		}

		comboViewer.insert(getDefaultValue(), 0);
		comboViewer.insert(messages.ControlAreaCombo_All_Objects_Value, 1);

		final Optional<ControlAreaValue> oldValue = values.stream()
				.filter(areaValue -> {
					if (oldSelectionValue instanceof String) {
						return areaValue.equals(oldSelectionValue);
					} else if (oldSelectionValue instanceof final ControlAreaValue oldAreaValue) {
						return areaValue.areaName()
								.equals(oldAreaValue.areaName());
					}
					return false;
				})
				.findFirst();
		if (!oldSelectionValue
				.equals(messages.ControlAreaCombo_All_Objects_Value)
				&& oldValue.isPresent()) {
			final int index = comboViewer.getCombo()
					.indexOf(oldValue.get().areaName());
			comboViewer.getCombo().select(index);
		} else {
			comboViewer.getCombo().select(0);
		}
		comboViewer.getCombo().setEnabled(true);
	}

	private List<ControlAreaValue> getDiffComboValues() {
		final List<ControlAreaValue> values = new LinkedList<>();
		if (getSession() == null) {
			return values;
		}
		final List<ControlAreaValue> initialValues = getComboValues(
				getSession(), ContainerType.INITIAL);
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
			final Optional<ControlAreaValue> initialArea = values.stream()
					.filter(area -> area.areaId().equals(finalAreaId))
					.findFirst();

			if (initialArea.isEmpty()) {
				String areaName = getStellBeteichBezeichnung(finalArea);
				if (areaName == null) {
					areaName = getDefaultAreaName(values.size());
				}
				values.add(new ControlAreaValue(areaName, finalAreaId));
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
	private List<ControlAreaValue> getComboValues(final IModelSession session,
			final ContainerType containerType) {
		if (session == null) {
			return Collections.emptyList();
		}
		final MultiContainer_AttributeGroup selectedContainer = session
				.getContainer(containerType);
		if (selectedContainer == null) {
			return Collections.emptyList();
		}

		this.container = selectedContainer;
		if (selectedContainer.getStellBereich() == null
				|| !selectedContainer.getStellBereich().iterator().hasNext()) {
			comboViewer.getCombo().setEnabled(false);
			return Collections.emptyList();
		}
		final List<ControlAreaValue> values = new LinkedList<>();
		int i = 1;
		for (final Stell_Bereich area : selectedContainer.getStellBereich()) {
			String areaName = getStellBeteichBezeichnung(area);
			if (areaName == null) {
				areaName = getDefaultAreaName(i);
			}
			values.add(new ControlAreaValue(areaName,
					area.getIdentitaet().getWert()));
			i++;
		}
		return values;
	}

	private void setEmtpyControlAreaCombo() {
		comboViewer.add(getDefaultValue());
		comboViewer.add(messages.ControlAreaCombo_All_Objects_Value);
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(true);
	}

	private void setSinglePlanControlAreaCombo() {
		comboViewer.add(messages.ControlAreaCombo_All_Objects_Value);
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(false);
	}

	private String getDefaultAreaName(final int index) {
		return String.format(messages.ControlAreaCombo_Default_Area_Name,
				Integer.valueOf(index));
	}

	private IModelSession getSession() {
		return application.getContext().get(IModelSession.class);
	}

	private void initCombo() {
		comboViewer.getCombo().removeAll();
		comboViewer
				.setInput(List.of(messages.TableTypeSelectionControl_noSession,
						messages.ControlAreaCombo_All_Objects_Value));
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(false);
		oldSelectionValue = getDefaultValue();
	}

	private void selectionControlArea(final SelectionChangedEvent e) {
		seletcionControlArea(e.getSelection(), tableType);
	}

	/**
	 * @return the default value of control area combo
	 */
	public String getDefaultValue() {
		return messages.ControlAreaCombo_PlanningArea_Value;
	}

	private void seletcionControlArea(final ISelection s,
			final TableType type) {
		if (s instanceof final IStructuredSelection selection) {
			final Object selectedElement = selection.getFirstElement();
			if (oldSelectionValue.equals(selectedElement) && tableType != null
					&& tableType.equals(type)) {
				return;
			}

			tableType = type;
			oldSelectionValue = selectedElement;
			if (selectedElement instanceof final ControlAreaValue selected) {
				// All objects in Control area should displayed regardless of
				// the planning area
				ToolboxEvents.send(broker, new SelectedControlAreaChangedEvent(
						selected, tableType, true));
				return;
			}
			if (selectedElement instanceof final String msg) {
				handleStringValue(msg);
			}
		}
	}

	private void handleStringValue(final String msg) {
		if (msg.equals(messages.ControlAreaCombo_All_Objects_Value)) {
			ToolboxEvents.send(broker,
					new SelectedControlAreaChangedEvent(tableType, true));
			return;
		}

		if (msg.equals(messages.ControlAreaCombo_PlanningArea_Value)) {
			ToolboxEvents.send(broker,
					new SelectedControlAreaChangedEvent(tableType, false));
			return;
		}

		if (msg.equals(messages.ControlAreaCombo_All_ControlArea)) {
			try {
				@SuppressWarnings("unchecked")
				final List<ControlAreaValue> allValues = List.class
						.cast(comboViewer.getInput());
				// Objects in all control area should displayed regardless of
				// the planning area
				ToolboxEvents.send(broker, new SelectedControlAreaChangedEvent(
						Set.copyOf(allValues), tableType, true));
			} catch (final Exception exc) {
				throw new RuntimeException(exc);
			}
		}
	}

	private void closeSession(final Event event) {
		final Object property = event.getProperty(IEventBroker.DATA);
		if (property instanceof final ToolboxFileRole role
				&& role.equals(ToolboxFileRole.SESSION)) {
			// Reset combo to default
			initCombo();
		}
	}
}
