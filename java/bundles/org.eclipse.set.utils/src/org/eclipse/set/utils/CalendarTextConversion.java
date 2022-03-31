/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.ecore.xml.type.internal.XMLCalendar;
import org.eclipse.swt.widgets.DateTime;

/**
 * Helper for calendar/text conversion.
 * 
 * @author Schaefer
 */
public class CalendarTextConversion {

	static DateTimeFormatter formatter = DateTimeFormatter
			.ofLocalizedDate(FormatStyle.MEDIUM);

	/**
	 * @param dateTime
	 *            the date time widget
	 * 
	 * @return the calendar
	 */
	public static Optional<XMLGregorianCalendar> getCalendar(
			final DateTime dateTime) {
		return getCalendar(dateTime.getYear(), dateTime.getMonth() + 1,
				dateTime.getDay());
	}

	/**
	 * @param text
	 *            the text
	 * 
	 * @return the calendar
	 */
	public static Optional<XMLGregorianCalendar> getCalendar(
			final String text) {
		try {
			final LocalDate date = LocalDate.parse(text, formatter);
			return getCalendar(date.getYear(), date.getMonthValue(),
					date.getDayOfMonth());
		} catch (final Exception e) {
			return Optional.empty();
		}
	}

	/**
	 * @param calendar
	 *            the calendar
	 * @return the text
	 */
	public static String getText(final XMLGregorianCalendar calendar) {
		final LocalDate date = LocalDate.of(calendar.getYear(),
				calendar.getMonth(), calendar.getDay());
		return date.format(formatter);
	}

	private static Optional<XMLGregorianCalendar> getCalendar(final int year,
			final int month, final int day) {
		final GregorianCalendar c = new GregorianCalendar();
		c.set(year, month - 1, day);
		final XMLGregorianCalendar xmlGregorianCalendar = new XMLCalendar(
				c.getTime(), XMLCalendar.DATE);
		xmlGregorianCalendar.setHour(DatatypeConstants.FIELD_UNDEFINED);
		xmlGregorianCalendar.setMinute(DatatypeConstants.FIELD_UNDEFINED);
		xmlGregorianCalendar.setSecond(DatatypeConstants.FIELD_UNDEFINED);
		xmlGregorianCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
		xmlGregorianCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return Optional.of(xmlGregorianCalendar);
	}
}
