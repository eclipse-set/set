/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.update;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.set.core.services.update.ConcreteModelUpdateService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.toolboxmodel.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Einzel;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt;
import org.eclipse.set.toolboxmodel.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.osgi.service.component.annotations.Component;

/**
 * create and add adapter(which listen to the change of the session) into
 * session
 * 
 * @author Truong
 *
 */
@Component(immediate = true)
public class ConcretePlanproModelUpdateServiceImpl
		implements ConcreteModelUpdateService {

	static boolean ignoreNotification = false;

	/**
	 * Set fachplanung.ausgabeFachdaten.untergewerkArt
	 * 
	 * @param schnittstelle
	 *            {@link PlanPro_Schnittstelle}
	 * @param value
	 *            new value
	 */
	public static void copyUntergewerkArtValues(
			final PlanPro_Schnittstelle schnittstelle, final Object value) {
		final Planung_Projekt lstPlanungProjekt = PlanProSchnittstelleExtensions
				.LSTPlanungProjekt(schnittstelle);
		Planung_Gruppe planungGruppe = null;
		if (lstPlanungProjekt != null) {
			final EList<Planung_Gruppe> groups = lstPlanungProjekt
					.getLSTPlanungGruppe();
			if (!groups.isEmpty()) {
				planungGruppe = groups.get(0);
			}
		}
		Planung_Einzel planungEinzel = null;
		if (planungGruppe != null) {
			planungEinzel = planungGruppe.getLSTPlanungEinzel();
		}
		Ausgabe_Fachdaten ausgabeFachdaten = null;
		if (planungEinzel != null) {
			ausgabeFachdaten = planungEinzel.getIDAusgabeFachdaten();
		}

		final Untergewerk_Art_TypeClass untergewerkArt = PlanProFactory.eINSTANCE
				.createUntergewerk_Art_TypeClass();
		if (value instanceof final ENUMUntergewerkArt uart) {
			untergewerkArt.setWert(uart);
		} else if (value instanceof final Untergewerk_Art_TypeClass uart) {
			untergewerkArt.setWert(uart.getWert());
		}
		if (ausgabeFachdaten != null) {
			try {
				ignoreNotification = true;
				ausgabeFachdaten.setUntergewerkArt(untergewerkArt);
			} finally {
				ignoreNotification = false;
			}

		}

	}

	/**
	 * @param feature
	 *            UntergewerkArt feature
	 * @param object
	 *            the Planpro
	 */
	public static void removeUntergewerkArt(final EStructuralFeature feature,
			final PlanPro_Schnittstelle object) {
		final List<Object> list = IteratorExtensions
				.toList(IteratorExtensions.filter(object.eAllContents(),
						Untergewerk_Art_TypeClass.class));
		for (final Object obj : list) {
			final EObject container = EObject.class.cast(obj).eContainer();
			container.eClass().getEAllReferences().forEach(ref -> {
				if (ref.getName().equals(feature.getName())) {
					container.eSet(ref, null);
				}
			});
		}
	}

	private static void addAdapter(final PlanPro_Schnittstelle object) {
		final Adapter adapter = new EContentAdapter() {

			@Override
			public void notifyChanged(final Notification notification) {
				super.notifyChanged(notification);
				if (ignoreNotification) {
					return;
				}
				if (!(notification
						.getFeature() instanceof EStructuralFeature)) {
					return;
				}
				final EStructuralFeature notifiedFeature = (EStructuralFeature) notification
						.getFeature();
				if (notifiedFeature == null) {
					return;
				}
				if (notifiedFeature.getEType().getInstanceClass()
						.isAssignableFrom(Untergewerk_Art_TypeClass.class)
						|| notification
								.getNotifier() instanceof Untergewerk_Art_TypeClass) {
					if (notification.getNewValue() != null) {
						copyUntergewerkArtValues(object,
								notification.getNewValue());
					} else {
						removeUntergewerkArt(notifiedFeature, object);
					}

				}

			}
		};
		object.eAdapters().add(adapter);
	}

	@Override
	public void add(final EObject model) {
		if (model != null && model instanceof PlanPro_Schnittstelle) {
			addAdapter((PlanPro_Schnittstelle) model);
		}
	}

}
