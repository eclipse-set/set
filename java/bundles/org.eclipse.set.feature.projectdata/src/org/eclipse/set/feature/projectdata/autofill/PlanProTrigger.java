/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.autofill;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Lists;

import org.eclipse.set.basis.autofill.ConfirmationTrigger;

class PlanProTrigger extends ConfirmationTrigger implements Consumer<Text> {

	private boolean enabled = true;
	private final boolean ignoreFocus;

	public PlanProTrigger(final BooleanSupplier confirmation,
			final boolean ignoreFocus) {
		super(confirmation);
		this.ignoreFocus = ignoreFocus;
	}

	@Override
	public void accept(final Text text) {
		if (ignoreFocus) {
			return;
		}
		final FocusAdapter focusAdapter = new FocusAdapter() {

			@Override
			public void focusLost(final FocusEvent e) {
				activate();
			}
		};
		text.addFocusListener(focusAdapter);
		text.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(final DisposeEvent e) {
				text.removeFocusListener(focusAdapter);
			}
		});
	}

	@Override
	public void activate() {
		if (enabled) {
			super.activate();
		}
	}

	public void addAdapter(final Control parent, final EObject object,
			final EStructuralFeature attributeFeature,
			final EStructuralFeature wertFeature) {
		final Adapter adapter = new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				final Object notifiedFeature = notification.getFeature();
				final ArrayList<EStructuralFeature> features = Lists
						.newArrayList(attributeFeature, wertFeature);
				if (features.contains(notifiedFeature)) {
					activate();
				}
			}
		};
		object.eAdapters().add(adapter);
		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(final DisposeEvent e) {
				object.eAdapters().remove(adapter);
			}
		});
	}

	public void setEnable(final boolean value) {
		enabled = value;
	}
}