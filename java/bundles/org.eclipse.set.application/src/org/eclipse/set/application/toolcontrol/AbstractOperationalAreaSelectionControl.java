/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.toolcontrol;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

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
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.NewTableTypeEvent;
import org.eclipse.set.utils.events.SelectedOperationalAreaChangedEvent.OperationalAreaComboValue;
import org.eclipse.set.utils.events.ToolboxEvent;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;

/**
 * Abstract class for create combo selection of operational area like
 * {@link Stell_Bereich} and {@link RBC}
 * 
 * @param <T>
 *            the type of operational area
 * @author truong
 * 
 */
public abstract class AbstractOperationalAreaSelectionControl<T extends Ur_Objekt> {
	private static class OperationalAreaLabelProvider extends LabelProvider {
		public OperationalAreaLabelProvider() {
			super();
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof final OperationalAreaComboValue value) {
				return value.areaName();
			}
			return super.getText(element);
		}

	}

	private final MApplication application;

	private final IEventBroker broker;

	protected ComboViewer comboViewer;

	@Translation
	protected final Messages messages;

	ToolboxPartService partService;
	private ToolboxEventHandler<NewTableTypeEvent> newTableTypeHandler;
	private TableType tableType;

	private Object oldSelectionValue;

	protected AbstractOperationalAreaSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		application = serviceProvider.application;
		broker = serviceProvider.broker;
		messages = serviceProvider.messages;
		partService = serviceProvider.partService;
		createCombo(parent);
		// Reset combo value, when close session
		broker.subscribe(Events.CLOSE_SESSION, this::closeSession);
	}

	protected void createCombo(final Composite parent) {
		comboViewer = new ComboViewer(parent);
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new OperationalAreaLabelProvider());
		initCombo();
		comboViewer.addSelectionChangedListener(this::selectionListerner);
		newTableTypeHandler = new DefaultToolboxEventHandler<>() {

			@Override
			public void accept(final NewTableTypeEvent t) {
				setCombo(t.getTableType());
				// Send update event, when table type change
				seletcionOperationalArea(comboViewer.getSelection(),
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

	protected void setCombo(final TableType type) {
		comboViewer.getCombo().removeAll();
		final List<OperationalAreaComboValue> values = new LinkedList<>();
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
				setSinglePlanOperationalAreaCombo();
				seletcionOperationalArea(comboViewer.getSelection(),
						TableType.SINGLE);
				return;
			case DIFF:
				values.addAll(getDiffComboValues());
				break;
			default:
				break;
		}
		if (values.isEmpty()) {
			setEmtpyOperationalAreaCombo();
			return;
		}

		comboViewer.setInput(values);
		if (values.size() > 1) {
			comboViewer.insert(messages.ControlAreaCombo_All_ControlArea,
					values.size());
		}

		comboViewer.insert(getDefaultValue(), 0);
		comboViewer.insert(messages.ControlAreaCombo_All_Objects_Value, 1);

		if (oldSelectionValue instanceof final String oldText) {
			final int i = comboViewer.getCombo().indexOf(oldText);
			comboViewer.getCombo().select(i >= 0 ? i : 0);
			comboViewer.getCombo().setEnabled(true);
			return;
		}

		int selectIndex = -1;

		if (oldSelectionValue instanceof final OperationalAreaComboValue oldArea) {
			for (final OperationalAreaComboValue v : values) {
				if (v.areaId() != null && v.areaId().equals(oldArea.areaId())) {
					selectIndex = comboViewer.getCombo().indexOf(v.areaName());
					break;
				}
			}
		}

		comboViewer.getCombo().select(selectIndex >= 0 ? selectIndex : 0);
		comboViewer.getCombo().setEnabled(true);
	}

	protected void seletcionOperationalArea(final ISelection s,
			final TableType type) {
		if (s instanceof final IStructuredSelection selection) {
			final Object selectedElement = selection.getFirstElement();
			if (oldSelectionValue.equals(selectedElement) && tableType != null
					&& tableType.equals(type)) {
				return;
			}

			tableType = type;
			oldSelectionValue = selectedElement;
			if (selectedElement instanceof final OperationalAreaComboValue selected) {
				// All objects in Control area should displayed regardless of
				// the planning area
				ToolboxEvents.send(broker, getSelectedOperationAreaEvent(
						selected, tableType, true));
				return;
			}
			if (selectedElement instanceof final String msg) {
				handleStringValue(msg);
			}
		}

	}

	protected abstract void handleStringValue(String msg);

	protected abstract ToolboxEvent getSelectedOperationAreaEvent(
			OperationalAreaComboValue selected, TableType type);

	private void selectionListerner(final SelectionChangedEvent e) {
		seletcionOperationalArea(e.getSelection(), tableType);
	}

	protected abstract void setSinglePlanOperationalAreaCombo();

	protected List<OperationalAreaComboValue> getComboValues(
			final IModelSession session, final ContainerType initial) {
		final MultiContainer_AttributeGroup container = PlanProSchnittstelleExtensions
				.getContainer(session.getPlanProSchnittstelle(), initial);
		final List<T> areas = getOperationalAreaFunction().apply(container);
		if (areas.isEmpty()) {
			comboViewer.getCombo().setEnabled(false);
			return Collections.emptyList();
		}
		final List<OperationalAreaComboValue> values = new LinkedList<>();
		for (int i = 0; i < areas.size(); i++) {
			String label = getOperationalAreaLabelFunction()
					.apply(areas.get(i));
			if (label == null || label.isEmpty()) {
				label = getDefaultAreaName(i);
			}
			values.add(new OperationalAreaComboValue(label,
					areas.get(i).getIdentitaet().getWert()));
		}
		return values;
	}

	protected abstract String getDefaultAreaName(int i);

	private List<OperationalAreaComboValue> getDiffComboValues() {
		final List<OperationalAreaComboValue> initialValues = getComboValues(
				getSession(), ContainerType.INITIAL);
		final List<OperationalAreaComboValue> finalValues = getComboValues(
				getSession(), ContainerType.FINAL);

		if (finalValues == null || finalValues.isEmpty()) {
			return initialValues;
		} else if (initialValues == null || initialValues.isEmpty()) {
			return finalValues;
		}

		final List<OperationalAreaComboValue> values = new LinkedList<>();
		values.addAll(initialValues);
		for (final OperationalAreaComboValue value : finalValues) {
			if (values.stream()
					.noneMatch(
							v -> v.areaId().equalsIgnoreCase(value.areaId()))) {
				values.add(value);
			}
		}
		return values;
	}

	protected abstract Function<MultiContainer_AttributeGroup, List<T>> getOperationalAreaFunction();

	protected abstract Function<T, String> getOperationalAreaLabelFunction();

	protected abstract void setEmtpyOperationalAreaCombo();

	protected abstract Object getDefaultValue();

	protected IModelSession getSession() {
		return application.getContext().get(IModelSession.class);
	}

	private void closeSession(final Event event) {
		final Object property = event.getProperty(IEventBroker.DATA);
		if (property instanceof final ToolboxFileRole role
				&& role.equals(ToolboxFileRole.SESSION)) {
			// Reset combo to default
			initCombo();
		}
	}

	protected abstract void initCombo();
}
