/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link CalendarTextConversion}.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class CalendarTextConversionTest {

	private GregorianCalendar exampleCalendar;
	private String exampleDateText;
	private DateTimeFormatter formatter;
	private String illegalDateText;

	/**
	 * Set up.
	 */
	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.GERMANY);
		exampleDateText = "09.10.2017";
		illegalDateText = "dies ist kein Datum";
		formatter = CalendarTextConversion.formatter;
		exampleCalendar = new GregorianCalendar();
		exampleCalendar.set(2017, 9, 9);
	}

	/**
	 * Test method for {@link CalendarTextConversion#getCalendar(String)}.
	 */
	@Test
	public void testGetCalendarExample() {
		final Optional<XMLGregorianCalendar> optionalCalendar = CalendarTextConversion
				.getCalendar(exampleDateText);
		assertTrue(optionalCalendar.isPresent());

		final LocalDate date = LocalDate.parse(exampleDateText, formatter);

		final XMLGregorianCalendar calendar = optionalCalendar.get();

		assertThat(Integer.valueOf(calendar.getYear()),
				is(Integer.valueOf(date.getYear())));
		assertThat(Integer.valueOf(calendar.getMonth()),
				is(Integer.valueOf(date.getMonthValue())));
		assertThat(Integer.valueOf(calendar.getDay()),
				is(Integer.valueOf(date.getDayOfMonth())));
		assertThat(Integer.valueOf(calendar.getHour()),
				is(Integer.valueOf(DatatypeConstants.FIELD_UNDEFINED)));
		assertThat(Integer.valueOf(calendar.getMinute()),
				is(Integer.valueOf(DatatypeConstants.FIELD_UNDEFINED)));
		assertThat(Integer.valueOf(calendar.getSecond()),
				is(Integer.valueOf(DatatypeConstants.FIELD_UNDEFINED)));
		assertThat(Integer.valueOf(calendar.getMillisecond()),
				is(Integer.valueOf(DatatypeConstants.FIELD_UNDEFINED)));
		assertThat(Integer.valueOf(calendar.getTimezone()),
				is(Integer.valueOf(DatatypeConstants.FIELD_UNDEFINED)));
	}

	/**
	 * Test method for {@link CalendarTextConversion#getCalendar(String)}.
	 */
	@Test
	public void testGetCalendarIllegal() {
		final Optional<XMLGregorianCalendar> optionalCalendar = CalendarTextConversion
				.getCalendar(illegalDateText);
		assertFalse(optionalCalendar.isPresent());
	}

	/**
	 * Test method for
	 * {@link CalendarTextConversion#getText(XMLGregorianCalendar)}.
	 * 
	 * @throws DatatypeConfigurationException
	 *             if the implementation is not available or cannot be
	 *             instantiated.
	 */
	@Test
	public void testGetText() throws DatatypeConfigurationException {
		final XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
				.newXMLGregorianCalendar(exampleCalendar);
		assertThat(CalendarTextConversion.getText(calendar),
				is(exampleDateText));
	}

}
