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
package org.eclipse.set.application.operationalarea;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.ppmodel.extensions.StellBereichExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.SelectedOperationalAreaChangedEvent;
import org.eclipse.set.utils.events.SelectedOperationalAreaChangedEvent.OperationalAreaComboValue;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Streams;

/**
 * Selection combo for the control area. The table will only show the objects,
 * which lie on this control area
 * 
 * @author Truong
 */
public class ControlAreaSelectionControl
		extends AbstractOperationalAreaSelectionControl<Stell_Bereich> {

	/**
	 * @param parent
	 *            the parent
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public ControlAreaSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		super(parent, serviceProvider);
	}

	@Override
	protected String getDefaultAreaName(final int index) {
		return String.format(messages.ControlAreaCombo_Default_Area_Name,
				Integer.valueOf(index));
	}

	@Override
	protected void handleStringValue(final String msg) {
		super.handleStringValue(msg);
		if (msg.equals(messages.ControlAreaCombo_All_ControlArea)) {
			try {
				@SuppressWarnings("unchecked")
				final List<OperationalAreaComboValue<Stell_Bereich>> allValues = List.class
						.cast(comboViewer.getInput());
				// Objects in all control area should displayed regardless of
				// the planning area
				ToolboxEvents.send(broker,
						new SelectedOperationalAreaChangedEvent(
								getOperationalAreaClass(),
								Set.copyOf(allValues), tableType, true));
			} catch (final Exception exc) {
				throw new RuntimeException(exc);
			}
		}
	}

	@Override
	protected Function<MultiContainer_AttributeGroup, List<Stell_Bereich>> getOperationalAreaFunction() {
		return container -> Streams.stream(container.getStellBereich())
				.toList();
	}

	@Override
	protected Function<Stell_Bereich, String> getOperationalAreaLabelFunction() {
		return StellBereichExtensions::getStellBeteichBezeichnung;
	}

	@Override
	protected Class<Stell_Bereich> getOperationalAreaClass() {
		return Stell_Bereich.class;
	}
}
