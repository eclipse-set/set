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
package org.eclipse.set.feature.projectdata.ppimport;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.model.planpro.Layoutinformationen.PlanPro_Layoutinfo;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Einzel;
import org.eclipse.set.ppmodel.extensions.ContainerExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProLayoutInfoExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions;
import org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * Count object of initial and final state in current session
 * 
 * @author Truong
 */
public class ModelInformationGroup {
	private Label countFinal;

	private Label countInitial;
	private final Messages messages;
	private final IModelSession session;

	private Label countLayout;

	/**
	 * Constructor
	 * 
	 * @param session
	 *            the current session
	 * @param messages
	 *            {@link Messages}
	 */
	public ModelInformationGroup(final IModelSession session,
			final Messages messages) {
		this.messages = messages;
		this.session = session;
	}

	/**
	 * Create information swt group
	 *
	 * @param parent
	 *            the parent swt
	 */
	// IMPROVE: the object count information should be determine all subwork
	public void createInfoGroup(final Composite parent) {
		final Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);
		group.setText(messages.PlanProImportPart_infoGroup);
		group.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		group.setLayout(new GridLayout());

		final PlanPro_Schnittstelle planProSchnittstelle = session
				.getPlanProSchnittstelle();
		final Planung_Einzel singlePlanningState = PlanungProjektExtensions
				.getLeadingPlanungGruppe(PlanProSchnittstelleExtensions
						.LSTPlanungProjekt(planProSchnittstelle))
				.getLSTPlanungEinzel();

		final Container_AttributeGroup initialContainer = PlanungEinzelExtensions
				.LSTZustandStart(singlePlanningState)
				.getContainer();
		final Container_AttributeGroup finalContainer = PlanungEinzelExtensions
				.LSTZustandZiel(singlePlanningState)
				.getContainer();
		final PlanPro_Layoutinfo layoutInfo = session.getLayoutInformation();

		countInitial = createCountInfo(group,
				messages.PlanProImportPart_countStart,
				ContainerExtensions.getSize(initialContainer));
		countFinal = createCountInfo(group,
				messages.PlanProImportPart_countZiel,
				ContainerExtensions.getSize(finalContainer));
		countLayout = createCountInfo(group,
				messages.PlanproImportPart_countLayout,
				PlanProLayoutInfoExtensions.getSize(layoutInfo));
	}

	private static Label createCountInfo(final Composite parent,
			final String text, final int size) {
		final Label labelText = new Label(parent, SWT.NONE);
		labelText.setText(text);
		final Label labelCount = new Label(parent, SWT.NONE);
		labelCount.setText(Integer.toString(size));
		return labelCount;
	}

	/**
	 * Update information after import
	 */
	public void updateInfoGroup() {
		final PlanPro_Schnittstelle planProSchnittstelle = session
				.getPlanProSchnittstelle();
		final Planung_Einzel singlePlanningState = PlanungProjektExtensions
				.getLeadingPlanungGruppe(PlanProSchnittstelleExtensions
						.LSTPlanungProjekt(planProSchnittstelle))
				.getLSTPlanungEinzel();

		final Container_AttributeGroup initialContainer = PlanungEinzelExtensions
				.LSTZustandStart(singlePlanningState)
				.getContainer();
		final Container_AttributeGroup finalContainer = PlanungEinzelExtensions
				.LSTZustandZiel(singlePlanningState)
				.getContainer();
		final PlanPro_Layoutinfo layoutInfo = session.getLayoutInformation();
		countInitial.setText(Integer
				.toString(ContainerExtensions.getSize(initialContainer)));
		countFinal.setText(
				Integer.toString(ContainerExtensions.getSize(finalContainer)));
		countLayout.setText(Integer
				.toString(PlanProLayoutInfoExtensions.getSize(layoutInfo)));
		countInitial.getParent().layout();
		countFinal.getParent().layout();
	}

	/**
	 * Not support info
	 * 
	 * @param parent
	 *            the swt parent
	 */
	public void createNotSupportedInfo(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(messages.PlanProImportPart_notSupportedInfo);
	}
}
