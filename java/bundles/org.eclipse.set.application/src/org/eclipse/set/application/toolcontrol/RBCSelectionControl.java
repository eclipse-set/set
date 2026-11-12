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

import java.util.List;
import java.util.function.Function;

import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.events.SelectedOperationalAreaChangedEvent.OperationalAreaComboValue;
import org.eclipse.set.utils.events.ToolboxEvent;
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
	 * @param serviceProvider
	 */
	public RBCSelectionControl(final Composite parent,
			final ServiceProvider serviceProvider) {
		super(parent, serviceProvider);
	}

	@Override
	protected void setEmtpyOperationalAreaCombo() {
		comboViewer.add(messages.RBC_Control_No_RBC);
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(false);
	}

	@Override
	public String getDefaultValue() {
		return messages.RBC_Control_All_RBC;
	}

	@Override
	protected String getDefaultAreaName(final int index) {
		return String.format(messages.RBCCombo_Default_RBC_Name,
				Integer.valueOf(index));
	}

	@Override
	protected void handleStringValue(final String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected ToolboxEvent getSelectedOperationAreaEvent(
			final OperationalAreaComboValue selected,
			final TableType tableType) {
		return null;
	}

	@Override
	protected void setSinglePlanOperationalAreaCombo() {
		comboViewer.add(getDefaultValue());
		comboViewer.getCombo().select(0);
		comboViewer.getCombo().setEnabled(true);
	}

	@Override
	protected void initCombo() {
		// TODO Auto-generated method stub

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

}
