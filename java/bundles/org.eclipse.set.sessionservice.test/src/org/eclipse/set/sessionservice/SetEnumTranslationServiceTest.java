/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.sessionservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.function.Function;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.set.core.enumtranslation.Enumerators;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.junit.jupiter.api.Test;

import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMBesonderesFahrwegelement;

/**
 * Tests for {@link SetEnumTranslationService}.
 * 
 * @author Schaefer
 */
class SetEnumTranslationServiceTest {

	private static final String BEWEGLICHE_BRUECKE = "BEWEGLICHE_BRUECKE"; //$NON-NLS-1$
	private SetEnumTranslationService testee;
	private EnumTranslation translation;

	/**
	 * Test for {@link SetEnumTranslationService#translate(Enumerator)}
	 */
	@Test
	void testTranslateEnumerator() {
		givenSetEnumTranslationService();

		whenTranslate(
				ENUMBesonderesFahrwegelement.ENUM_BESONDERES_FAHRWEGELEMENT_BEWEGLICHE_BRÜCKE);
		thenExpect(EnumTranslation::getAlternative, BEWEGLICHE_BRUECKE);
	}

	private void givenSetEnumTranslationService() {
		testee = new SetEnumTranslationService();

		// We don't care about the real life translations. This test is about
		// checking the key compilation and correct matching with the
		// translation elements.
		final Enumerators enumerators = new Enumerators();
		enumerators.ENUMBesonderes_Fahrwegelement_bewegliche_Bruecke_Alternative = BEWEGLICHE_BRUECKE;
		testee.setEnumerators(enumerators);
	}

	private void thenExpect(final Function<EnumTranslation, String> type,
			final String value) {
		assertThat(type.apply(translation), is(value));
	}

	private void whenTranslate(final Enumerator value) {
		translation = testee.translate(value);
	}
}
