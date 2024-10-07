/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.model.planpro.Bedienung.Bedien_Anrueckabschnitt

/**
 * Extensions for {@link Bearbeitungsvermerk}.
 */
class BearbeitungsvermerkExtensions extends BasisObjektExtensions {

	static def String getFootnoteText(Bearbeitungsvermerk footnote) {
		return footnote?.bearbeitungsvermerkAllg?.kommentar?.wert
	}

	static def String getIndexedFootnoteText(Bearbeitungsvermerk footnote,
		int index) {
		return footnote?.bearbeitungsvermerkAllg?.kommentar?.wert
	}

	static def String getIndexedFootnoteText(
		Pair<Integer, Bearbeitungsvermerk> pair) {
		return getIndexedFootnoteText(pair.value, pair.key)
	}
}
