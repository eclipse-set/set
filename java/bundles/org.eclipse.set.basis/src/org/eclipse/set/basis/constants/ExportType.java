/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

/**
 * Indicate the type of an document export.
 * 
 * @author Schaefer
 */
public enum ExportType {

	/**
	 * Bestandsunterlagen
	 */
	INVENTORY_RECORDS,

	/**
	 * Dokumentensatz für Einzelzustands der PlanPro-Datei
	 * (Start/Ziel/Differenz)
	 */
	PLANNING_RECORDS
}
