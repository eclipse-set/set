/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.exception

import de.scheidtbachmann.planpro.model.model1902.Signale.Signal_Befestigung
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal_Rahmen
import java.util.List

import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Multiple Signalrahmen for Befestigungsgruppe. 
 * 
 * @author Schaefer
 */
class MultipleRahmenForGruppe extends RuntimeException {
	
	new(List<Signal_Rahmen> rahmen, List<Signal_Befestigung> gruppe) {
		super('''rahmen=«rahmen.debugString» gruppe=«gruppe.debugString»''')
	}
}
