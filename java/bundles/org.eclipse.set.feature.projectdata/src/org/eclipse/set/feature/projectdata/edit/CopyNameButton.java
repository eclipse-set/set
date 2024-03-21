/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.edit;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emf.ecp.view.spi.model.VFeaturePathDomainModelReference;
import org.eclipse.set.basis.emfforms.RendererContext;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.ppmodel.extensions.utils.NameAkteurTransformation;
import org.eclipse.set.model.planpro.PlanPro.Akteur_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_10_TypeClass;
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_5_TypeClass;
import org.eclipse.set.model.planpro.PlanPro.Name_Akteur_TypeClass;
import org.eclipse.set.utils.ButtonAction;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Maps;

/**
 * The {@link ButtonAction} for copy name to name5/name10.
 * 
 * @author Schaefer
 */
public class CopyNameButton implements ButtonAction {

	private static final int BUTTON_WIDTH = 150;

	private final Messages messages;
	private final boolean enable;

	final Map<Button, RendererContext> buttonsToContext = Maps.newHashMap();

	/**
	 * @param messages
	 *            the translations
	 * @param enable
	 *            should button enable or not
	 */
	public CopyNameButton(final Messages messages, final boolean enable) {
		this.messages = messages;
		this.enable = enable;
	}

	@Override
	public String getText() {
		return messages.CopyNameButton_Text;
	}

	@Override
	public int getWidth() {
		return BUTTON_WIDTH;
	}

	@Override
	public void register(final RendererContext context) {
		final Button button = context.get(Button.class);
		button.setEnabled(enable);
		buttonsToContext.put(button, context);
		button.addDisposeListener(e -> buttonsToContext.remove(button));
	}

	/**
	 * Refresh button after name akteur change
	 * 
	 * @param notification
	 *            the notification
	 */
	public void refresh(final Notification notification) {
		final Object notifier = notification.getNotifier();

		if (notifier instanceof final Akteur_Allg_AttributeGroup akteurAllg) {
			setButtonState(akteurAllg, akteurAllg.getNameAkteur());
		}

		if (notifier instanceof final Name_Akteur_TypeClass nameAkteur
				&& nameAkteur
						.eContainer() instanceof final Akteur_Allg_AttributeGroup akteurAllg) {
			setButtonState(akteurAllg, nameAkteur);
		}
	}

	private void setButtonState(final Akteur_Allg_AttributeGroup aktuerAllg,
			final Name_Akteur_TypeClass nameAkteur) {
		final boolean shouldEnable = nameAkteur != null
				&& nameAkteur.getWert() != null
				&& !nameAkteur.getWert().isBlank();

		buttonsToContext.forEach((button, action) -> {
			if (action.get(
					EObject.class) instanceof final Akteur_Allg_AttributeGroup buttonAkteurAllg
					&& aktuerAllg == buttonAkteurAllg && !button.isDisposed()
					&& button.getEnabled() != shouldEnable) {
				button.setEnabled(shouldEnable);
			}
		});
	}

	@Override
	public void selected(final SelectionEvent e) {
		final RendererContext rendererContext = buttonsToContext
				.get(e.getSource());
		final Akteur_Allg_AttributeGroup akteur = (Akteur_Allg_AttributeGroup) rendererContext
				.get(EObject.class);
		final Name_Akteur_TypeClass nameAkteur = akteur.getNameAkteur();
		final Text text = rendererContext.get(Text.class);
		final VControl vControl = rendererContext.get(VControl.class);
		final VFeaturePathDomainModelReference domainModelReference = (VFeaturePathDomainModelReference) vControl
				.getDomainModelReference();
		final Class<?> type = domainModelReference.getDomainModelEFeature()
				.getEType().getInstanceClass();
		final NameAkteurTransformation transformation = new NameAkteurTransformation();
		final Object transformed = transformation.transform(nameAkteur, type);
		if (transformed instanceof final Name_Akteur_5_TypeClass nameAkteur5) {
			text.setText(nameAkteur5.getWert());
		} else if (transformed instanceof final Name_Akteur_10_TypeClass nameAkteur10) {
			text.setText(nameAkteur10.getWert());
		}
	}
}
