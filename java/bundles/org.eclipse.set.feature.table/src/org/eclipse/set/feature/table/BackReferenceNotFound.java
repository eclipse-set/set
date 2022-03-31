/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt;

/**
 * A backward reference was not found.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls") // exceptions will not be translated
public class BackReferenceNotFound extends RuntimeException {

	BackReferenceNotFound(final Ur_Objekt object, final Class<?> type) {
		super(String.format("No %s references %s.id=%s.", type.getSimpleName(),
				object.eClass().getName(), object.getIdentitaet().getWert()));
	}
}
