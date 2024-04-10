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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.AbstractEnumerator;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.utils.widgets.ComboValues;

/**
 * Combo values for selection sub work type
 * 
 * @author Truong
 */
public class SubworkComboSelection extends AbstractEnumerator {

	/**
	 * Not selected
	 */
	public static String NOT_SELECTED_SUBWORK = "NOT_SELECTED"; //$NON-NLS-1$

	/**
	 * Unknow sub work art
	 */
	public static String NOT_SET_SUBWORK = "NOT_SET"; //$NON-NLS-1$

	protected SubworkComboSelection(final int value, final String name,
			final String literal) {
		super(value, name, literal);
	}

	private static class SubworkComboValues
			implements ComboValues<SubworkComboSelection> {
		private final PlanProFileNature fileNature;
		private final PlanPro_Schnittstelle schnittStelle;
		private final Messages messages;
		private List<SubworkComboSelection> itemValues;

		public SubworkComboValues(final PlanPro_Schnittstelle schnittStelle,
				final PlanProFileNature fileNature, final Messages messages) {
			this.schnittStelle = schnittStelle;
			this.fileNature = fileNature;
			this.messages = messages;
			createItems();
		}

		public SubworkComboValues(final Messages messages,
				final List<SubworkComboSelection> values) {
			this.schnittStelle = null;
			this.fileNature = null;
			this.messages = messages;
			createItems(values);
		}

		private void createItems() {
			switch (fileNature) {
			case INVALID:
				createItems(List.of(new SubworkComboSelection(0,
						NOT_SELECTED_SUBWORK,
						messages.PlanProImportPart_Subwork_NotSelected)));
				break;
			case INFORMATION_STATE:
				createItems(
						List.of(new SubworkComboSelection(0, NOT_SET_SUBWORK,
								messages.PlanproImportPart_Subwork_Notset)));
				break;
			case PLANNING:
				final List<SubworkComboSelection> test = new ArrayList<>();
				test.add(new SubworkComboSelection(0, NOT_SELECTED_SUBWORK,
						messages.ContainerValues_NotSelected));
				test.addAll(getSubworks());
				createItems(test);
				break;
			default:
				throw new IllegalArgumentException(fileNature.toString());
			}

		}

		private void createItems(final List<SubworkComboSelection> items) {
			itemValues = new LinkedList<>();
			items.forEach(itemValues::add);
		}

		private List<SubworkComboSelection> getSubworks() {
			try {
				final List<Ausgabe_Fachdaten> ausgabeFachdaten = schnittStelle
						.getLSTPlanung().getFachdaten().getAusgabeFachdaten();
				return ausgabeFachdaten.stream().map(fachdaten -> {
					try {
						final ENUMUntergewerkArt subwork = fachdaten
								.getUntergewerkArt().getWert();
						return new SubworkComboSelection(
								ausgabeFachdaten.indexOf(fachdaten) + 1,
								subwork.getName(), subwork.getLiteral());
					} catch (final NullPointerException e) {
						return new SubworkComboSelection(
								ausgabeFachdaten.indexOf(fachdaten) + 1,
								NOT_SET_SUBWORK,
								messages.PlanproImportPart_Subwork_Notset);
					}

				}).toList();
			} catch (final Exception e) {
				return Collections.emptyList();
			}
		}

		@Override
		public int getDefault() {
			if (fileNature == null) {
				return 0;
			}
			if (fileNature == PlanProFileNature.INFORMATION_STATE) {
				return getValue(NOT_SET_SUBWORK).getValue();
			}
			return getValue(NOT_SELECTED_SUBWORK).getValue();
		}

		@Override
		public int getIndex(final SubworkComboSelection value) {
			return value.getValue();
		}

		@Override
		public String[] getItems() {
			return itemValues.stream().map(SubworkComboSelection::getLiteral)
					.toArray(String[]::new);
		}

		@Override
		public SubworkComboSelection getValue(final int selectionIndex) {
			final Optional<SubworkComboSelection> first = itemValues.stream()
					.filter(e -> e.getValue() == selectionIndex).findFirst();
			if (first.isEmpty()) {
				return null;
			}
			return first.get();
		}

		/**
		 * @param name
		 *            the name of value
		 * 
		 * @return the value
		 */
		public SubworkComboSelection getValue(final String name) {
			final Optional<SubworkComboSelection> first = itemValues.stream()
					.filter(e -> e.getName().equals(name)).findFirst();
			if (first.isEmpty()) {
				return null;
			}
			return first.get();
		}
	}

	/**
	 * Get combo values
	 * 
	 * @param schnittstelle
	 *            the planpro schnittstelle
	 * @param fileNature
	 *            the file nature
	 * @param messages
	 *            the messages
	 * 
	 * @return the combo values
	 */
	public static ComboValues<SubworkComboSelection> getComboValues(
			final PlanPro_Schnittstelle schnittstelle,
			final PlanProFileNature fileNature, final Messages messages) {
		return new SubworkComboValues(schnittstelle, fileNature, messages);
	}

	/**
	 * Get default combo value
	 * 
	 * @param messages
	 *            the messages
	 * @return the combo values
	 */
	public static ComboValues<SubworkComboSelection> getDefaultValues(
			final Messages messages) {
		return new SubworkComboValues(null, PlanProFileNature.INVALID,
				messages);
	}

	/**
	 * Get combo values with given values
	 * 
	 * @param message
	 *            the message
	 * @param values
	 *            the values
	 * @return the combo values
	 */
	public static ComboValues<SubworkComboSelection> getComboValues(
			final Messages message, final List<SubworkComboSelection> values) {
		return new SubworkComboValues(message, values);
	}
}
