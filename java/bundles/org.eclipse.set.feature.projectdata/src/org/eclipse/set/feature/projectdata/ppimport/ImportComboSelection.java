/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.projectdata.ppimport;

import java.util.Arrays;

import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.utils.widgets.ComboValues;

/**
 * Possible container selections without a chosen project file.
 * 
 * @author Schaefer
 */
public enum ImportComboSelection {

	/**
	 * Not selected
	 */
	NOT_SELECTED,

	/**
	 * The Startcontainer
	 */
	START,

	/**
	 * The Zielcontainer
	 */
	ZIEL,

	/**
	 * The Zustand-Information Container
	 */
	ZUSTAND_INFORMATION;

	private static class ContainerComboValues
			implements ComboValues<ImportComboSelection> {

		private final PlanProFileNature fileNature;
		private String[] itemsMessages;
		private ImportComboSelection[] itemValues;
		private final Messages messages;

		public ContainerComboValues(final PlanProFileNature fileNature,
				final Messages messages) {
			this.fileNature = fileNature;
			this.messages = messages;
			createItems();
		}

		@Override
		public int getDefault() {
			return getIndex(NOT_SELECTED);
		}

		@Override
		public int getIndex(final ImportComboSelection value) {
			return Arrays.asList(itemValues).indexOf(value);
		}

		@Override
		public String[] getItems() {
			return itemsMessages;
		}

		@Override
		public ImportComboSelection getValue(final int selectionIndex) {
			return itemValues[selectionIndex];
		}

		private void createItems() {
			switch (fileNature) {
			case INVALID:
				createItems(NOT_SELECTED);
				break;
			case INFORMATION_STATE:
				createItems(NOT_SELECTED, ZUSTAND_INFORMATION);
				break;
			case PLANNING:
				createItems(NOT_SELECTED, START, ZIEL);
				break;
			default:
				throw new IllegalArgumentException(fileNature.toString());
			}
		}

		private void createItems(final ImportComboSelection... values) {
			itemValues = new ImportComboSelection[values.length];
			itemsMessages = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				itemValues[i] = values[i];
				itemsMessages[i] = getMessage(values[i]);
			}
		}

		private String getMessage(final ImportComboSelection value) {
			switch (value) {
			case NOT_SELECTED:
				return messages.ContainerValues_NotSelected;
			case START:
				return messages.ContainerValues_Start;
			case ZIEL:
				return messages.ContainerValues_Ziel;
			case ZUSTAND_INFORMATION:
				return messages.ContainerValues_ZustandInformation;
			default:
				throw new IllegalArgumentException(value.toString());
			}
		}

	}

	/**
	 * @param fileNature
	 *            the file nature
	 * @param messages
	 *            the messages
	 * 
	 * @return the combo values
	 */
	public static ComboValues<ImportComboSelection> getComboValues(
			final PlanProFileNature fileNature, final Messages messages) {
		return new ContainerComboValues(fileNature, messages);
	}
}
