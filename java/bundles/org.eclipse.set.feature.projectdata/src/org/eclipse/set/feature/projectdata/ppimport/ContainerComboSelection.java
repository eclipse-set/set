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
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.set.basis.constants.PlanProFileNature;
import org.eclipse.set.feature.projectdata.Messages;
import org.eclipse.set.utils.widgets.ComboValues;

/**
 * Possible container selections without a chosen project file.
 * 
 * @author Schaefer
 */
public enum ContainerComboSelection {
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
			implements ComboValues<ContainerComboSelection> {

		private final PlanProFileNature fileNature;
		private String[] itemsMessages;
		private ContainerComboSelection[] itemValues;
		private final Messages messages;

		public ContainerComboValues(final PlanProFileNature fileNature,
				final Messages messages) {
			this.fileNature = fileNature;
			this.messages = messages;
			createItems();
		}

		@Override
		public int getDefaultIndex() {
			if (fileNature == PlanProFileNature.INFORMATION_STATE) {
				return getIndex(ZUSTAND_INFORMATION);
			}
			return getIndex(NOT_SELECTED);
		}

		@Override
		public String getDefaultValue() {
			return getMessage(NOT_SELECTED);
		}

		@Override
		public int getIndex(final ContainerComboSelection value) {
			return Arrays.asList(itemValues).indexOf(value);
		}

		@Override
		public int getIndex(final String stringValue) {
			return ArrayUtils.indexOf(itemsMessages, stringValue);
		}

		@Override
		public String[] getComboValues() {
			return itemsMessages;
		}

		@Override
		public String[] getValuesWithoutDefault() {
			return ArrayUtils.removeElement(itemsMessages,
					getValue(getDefaultIndex()));
		}

		@Override
		public ContainerComboSelection getValue(final int selectionIndex) {
			return itemValues[selectionIndex];
		}

		@Override
		public ContainerComboSelection getValue(final String valueString) {
			for (final ContainerComboSelection value : itemValues) {
				if (getMessage(value).equals(valueString)) {
					return value;
				}
			}
			return null;
		}

		@Override
		public int size() {
			return itemValues.length;
		}

		private void createItems() {
			switch (fileNature) {
			case INVALID:
				createItems(NOT_SELECTED);
				break;
			case INFORMATION_STATE:
				createItems(ZUSTAND_INFORMATION);
				break;
			case PLANNING:
				createItems(NOT_SELECTED, START, ZIEL);
				break;
			default:
				throw new IllegalArgumentException(fileNature.toString());
			}
		}

		private void createItems(final ContainerComboSelection... values) {
			itemValues = new ContainerComboSelection[values.length];
			itemsMessages = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				itemValues[i] = values[i];
				itemsMessages[i] = getMessage(values[i]);
			}
		}

		private String getMessage(final ContainerComboSelection value) {
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

		@Override
		public String getMaxLengthItem() {
			return List.of(getMessage(NOT_SELECTED), getMessage(START),
					getMessage(ZIEL), getMessage(ZUSTAND_INFORMATION)).stream().reduce((a, b) -> a.length() > b.length() ? a : b).orElse(getDefaultValue());
		}
	}

	/**
	 * Get combo to selection container
	 * 
	 * @param fileNature
	 *            the file nature
	 * @param messages
	 *            the messages
	 * 
	 * @return the combo values
	 */
	public static ComboValues<ContainerComboSelection> getComboValues(
			final PlanProFileNature fileNature, final Messages messages) {
		return new ContainerComboValues(fileNature, messages);
	}

	/**
	 * Get combo with default values
	 * 
	 * @param messages
	 *            the messages
	 * @return the combo values
	 */
	public static ComboValues<ContainerComboSelection> getDefaultValues(
			final Messages messages) {
		return new ContainerComboValues(PlanProFileNature.INVALID, messages);
	}
}
