/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.autofill;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.set.basis.autofill.CopyValue;
import org.eclipse.set.basis.autofill.DefaultAutofill;
import org.eclipse.set.basis.autofill.FillPathSetting;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProFactory;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * PlanPro specific auto filling.
 * 
 * @author Schaefer
 */
public class DateAutofill extends DefaultAutofill {

	private static final int MIN_DATE = 1000;

	private static boolean isValidDate(final Object value) {
		if (value == null) {
			return false;
		}
		final XMLGregorianCalendar date = (XMLGregorianCalendar) value;
		if (date.getYear() > MIN_DATE) {
			return true;
		}
		return false;
	}

	private Planung_Projekt planning;

	private final PlanProTrigger trigger;

	/**
	 * Create PlanPro specific auto filling.
	 * 
	 * @param confirmation
	 *            the confirmation
	 * @param exceptionHandler
	 *            the exception handler used when executing instructions
	 */
	public DateAutofill(final Predicate<String> confirmation,
			final Consumer<Exception> exceptionHandler) {
		super(exceptionHandler);
		this.trigger = new PlanProTrigger(() -> confirmation.test(getDate()),
				true);
	}

	/**
	 * @return the view control listener
	 */
	public Consumer<Text> getTextConsumer() {
		return trigger;
	}

	/**
	 * @param value
	 *            the new enable value
	 */
	public void setEnable(final boolean value) {
		trigger.setEnable(value);
	}

	/**
	 * @param parent
	 *            the parent
	 * @param planning
	 *            the PlanPro planning
	 */
	public void setPlanning(final Control parent,
			final Planung_Projekt planning) {
		this.planning = planning;
		trigger.addAdapter(parent, planning, PlanProPackage.eINSTANCE
				.getPlanung_P_Allg_AttributeGroup_DatumAbschlussProjekt(),
				PlanProPackage.eINSTANCE
						.getDatum_Abschluss_Projekt_TypeClass_Wert());
		final FillPathSetting source = new FillPathSetting(
				PlanProFactory.eINSTANCE, planning,
				PlanProPackage.eINSTANCE.getPlanung_Projekt_PlanungPAllg(),
				PlanProPackage.eINSTANCE
						.getPlanung_P_Allg_AttributeGroup_DatumAbschlussProjekt(),
				PlanProPackage.eINSTANCE
						.getDatum_Abschluss_Projekt_TypeClass_Wert());
		trigger.addCondition(() -> isValidDate(source.getValue()));

		final FillPathSetting targetDatumAbschlussGruppe = new FillPathSetting(
				PlanProFactory.eINSTANCE, planning,
				PlanProPackage.eINSTANCE
						.getPlanung_Projekt_LstPlanungErsteGruppe(),
				PlanProPackage.eINSTANCE.getPlanung_Gruppe_PlanungGAllg(),
				PlanProPackage.eINSTANCE
						.getPlanung_G_Allg_AttributeGroup_DatumAbschlussGruppe(),
				PlanProPackage.eINSTANCE
						.getDatum_Abschluss_Gruppe_TypeClass_Wert());
		addFillingInstruction(
				new CopyValue(trigger, source, targetDatumAbschlussGruppe));

		final FillPathSetting targetDatumAbschlussEinzel = new FillPathSetting(
				PlanProFactory.eINSTANCE, planning,
				PlanProPackage.eINSTANCE
						.getPlanung_Projekt_LstPlanungErsteGruppe(),
				PlanProPackage.eINSTANCE.getPlanung_Gruppe_LSTPlanungEinzel(),
				PlanProPackage.eINSTANCE.getPlanung_Einzel_PlanungEAllg(),
				PlanProPackage.eINSTANCE
						.getPlanung_E_Allg_AttributeGroup_DatumAbschlussEinzel(),
				PlanProPackage.eINSTANCE
						.getDatum_Abschluss_Einzel_TypeClass_Wert());
		addFillingInstruction(
				new CopyValue(trigger, source, targetDatumAbschlussEinzel));
	}

	private String getDate() {
		final XMLGregorianCalendar date = planning.getPlanungPAllg()
				.getDatumAbschlussProjekt().getWert();
		final LocalDate localDate = LocalDate.of(date.getYear(),
				date.getMonth(), date.getDay());
		return localDate
				.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
	}
}
