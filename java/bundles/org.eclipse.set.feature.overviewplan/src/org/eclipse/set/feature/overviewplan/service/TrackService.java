/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.service;

import org.eclipse.set.feature.overviewplan.transformator.TOPKanteMetaData;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;

/**
 * @author truong
 *
 */
public interface TrackService {
	/**
	 * Get TOPKanteMetaData belong to this TOPKante
	 * 
	 * @param topKante
	 * @return the TOPKanteMetaData
	 */
	TOPKanteMetaData getTOPKanteMetaData(TOP_Kante topKante);
}
