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
package org.eclipse.set.application.operationalarea;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.set.application.toolcontrol.ServiceProvider;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.SelectedOperationalAreaChangedEvent;
import org.eclipse.set.utils.events.SelectedOperationalAreaChangedEvent.OperationalAreaComboValue;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Streams;

/**
 * 
 */
public class RBCSelectionControl
		extends AbstractOperationalAreaSelectionControl<RBC> {
	static final Logger LOGGER = LoggerFactory
			.getLogger(RBCSelectionControl.class);

	/**
	 * @param parent
	 *            the parent composite
	 * @param serviceProvider
	 *            the {@link ServiceProvider}
	 */
	public RBCSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		super(parent, serviceProvider);
	}

	@Override
	protected String getDefaultAreaName(final int index) {
		return String.format(messages.RBCCombo_Default_RBC_Name,
				Integer.valueOf(index));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleStringValue(final String msg) {
		super.handleStringValue(msg);
		if (msg.equals(messages.RBC_Control_All_RBC)) {
			final Object input = comboViewer.getInput();
			if (input instanceof final List<?> listInput && listInput
					.getFirst() instanceof OperationalAreaComboValue<?>) {
				ToolboxEvents.send(broker,
						new SelectedOperationalAreaChangedEvent(
								getOperationalAreaClass(),
								Set.copyOf(
										(List<OperationalAreaComboValue<RBC>>) listInput),
								tableType, false));
			}
		}
	}

	@Override
	protected Function<MultiContainer_AttributeGroup, List<RBC>> getOperationalAreaFunction() {
		return container -> Streams.stream(container.getRBC()).toList();
	}

	@Override
	protected Function<RBC, String> getOperationalAreaLabelFunction() {
		return rbc -> EObjectExtensions
				.getNullableObject(rbc,
						r -> r.getBezeichnung().getBezeichnungRBC().getWert())
				.orElse(null);
	}

	@Override
	protected Class<RBC> getOperationalAreaClass() {
		return RBC.class;
	}

}
